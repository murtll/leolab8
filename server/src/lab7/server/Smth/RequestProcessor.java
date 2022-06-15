package lab7.server.Smth;


import lab7.server.Exceptions.InvalidCredentialsException;
import lab7.server.Network.ClientRequestDto;
import lab7.server.Network.DatagramServer;
import lab7.server.Network.ServerResponseDto;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RequestProcessor {
    private final ThreadPoolExecutor readRequestsPool;
    private final ThreadPoolExecutor processRequestsPool;
    private final ThreadPoolExecutor sendResponsesPool;

    private final Logger logger;

    private final AuthenticationManager authenticator;
    private final AuthorizationManager authorizer;


    public RequestProcessor(Logger logger, AuthenticationManager authenticator, AuthorizationManager authorizationManager) {
        this.logger = logger;
        this.authenticator = authenticator;
        this.authorizer = authorizationManager;
        this.readRequestsPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.processRequestsPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        this.sendResponsesPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    private void processRequest(ClientRequestDto request, DatagramServer server) {
        if (request.getMeta() == null) {
            sendResponsesPool.submit(() -> {
                try {
                    server.sendResponse(request.getIp(), request.getPort(), new ServerResponseDto(false, "You must be logged in before executing some commands!"));
                } catch (IOException e) {
                    logger.error("Error sending data");
                }
            });
            return;
        }

        ServerResponseDto result;

        try {
            if (request.getData().getCommand().equals("login")) {
                authenticator.authenticate(request.getMeta());
                result = new ServerResponseDto(true, "Login successful");
            } else if (request.getData().getCommand().equals("register")) {
                authenticator.registerUser(request.getMeta());
                result = new ServerResponseDto(true, "Registration successful");
            } else {
                long userId = authenticator.authenticate(request.getMeta());

                if (authorizer.isAuthorized(userId, request.getData())) {
                    result = Commander.categorizeCommand(request.getData(), userId);
                } else {
                    result = new ServerResponseDto(false, "You are not authorized to use this command");
                }
            }
        } catch (InvalidCredentialsException e) {
            result = new ServerResponseDto(false, e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error while executing script on database");
            result = new ServerResponseDto(false, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("Error - no algorithm to hash data");
            result = new ServerResponseDto(false, e.getMessage());
        }

//        idk why but java requires this due to "Variables used in lambda must be final or effectively final"
        ServerResponseDto finalResult = result;

        sendResponsesPool.submit(() -> {
            try {
                server.sendResponse(request.getIp(), request.getPort(), finalResult);
            } catch (IOException e) {
                logger.error("Error sending data");
            }
        });
    }

    public void submitToReadRequestsPool(Runnable r) {
        readRequestsPool.submit(r);
    }

    public void addRequestToProcess(ClientRequestDto dto, DatagramServer server) {
        processRequestsPool.submit(() -> processRequest(dto, server));
    }

    public void stop() {
        readRequestsPool.shutdown();
        processRequestsPool.shutdown();
        sendResponsesPool.shutdown();
        logger.info("Process request thread stopped");
    }
}
