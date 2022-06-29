package lab7.server.Smth;


import lab7.server.Exceptions.InvalidCredentialsException;
import lab7.server.Network.ClientMetaDto;
import lab7.server.Network.ClientRequestDto;
import lab7.server.Network.DatagramServer;
import lab7.server.Network.Events.EventDataDto;
import lab7.server.Network.Events.EventSender;
import lab7.server.Network.Events.EventType;
import lab7.server.Network.ServerResponseDto;
import lab7.server.Security.AuthenticationManager;
import lab7.server.Security.AuthorizationManager;
import lab7.server.Security.CommandValidator;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RequestProcessor {
    private final ThreadPoolExecutor readRequestsPool;
    private final ThreadPoolExecutor processRequestsPool;
    private final ThreadPoolExecutor sendResponsesPool;

    private final Logger logger;

    private final AuthenticationManager authenticator;
    private final AuthorizationManager authorizer;

    private final EventSender eventSender;


    public RequestProcessor(Logger logger, AuthenticationManager authenticator, AuthorizationManager authorizationManager, EventSender eventSender) {
        this.logger = logger;
        this.authenticator = authenticator;
        this.authorizer = authorizationManager;
        this.readRequestsPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.processRequestsPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        this.sendResponsesPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        this.eventSender = eventSender;
        eventSender.startAccepting();
    }

    private void processRequest(ClientRequestDto request, DatagramServer server) {
        if (request.getMeta() == null) {
            sendResponsesPool.submit(() -> {
                try {
                    server.sendResponse(request.getIp(), request.getPort(), new ServerResponseDto(false, "not_authenticated"));
                } catch (IOException e) {
                    logger.error("Error sending data");
                }
            });
            return;
        }

        ServerResponseDto result;

        try {
            if (request.getData().getCommand().equals("login")) {
                ClientMetaDto clientMeta = authenticator.authenticate(request.getMeta());
                result = new ServerResponseDto(true, clientMeta.getId(), clientMeta.getColor());
            } else if (request.getData().getCommand().equals("register")) {
                ClientMetaDto clientMeta = authenticator.registerUser(request.getMeta());
                result = new ServerResponseDto(true, clientMeta.getId(), clientMeta.getColor());
            } else {
                ClientMetaDto clientMeta = authenticator.authenticate(request.getMeta());

                if (authorizer.isAuthorized(clientMeta.getId(), request.getData())) {
                    result = Commander.categorizeCommand(request.getData(), clientMeta.getId());
                } else {
                    result = new ServerResponseDto(false, "not_authorized");
                }
            }
        } catch (InvalidCredentialsException e) {
            result = new ServerResponseDto(false, "invalid_credentials");
        } catch (SQLException e) {
            logger.error("Error while executing script on database");
            result = new ServerResponseDto(false, "not_unique");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            logger.error("Error - no algorithm to hash data");
            result = new ServerResponseDto(false, "server_error");
        }

//        idk why but java requires this due to "Variables used in lambda must be final or effectively final"
        ServerResponseDto finalResult = result;

        sendResponsesPool.submit(() -> {
            try {
                server.sendResponse(request.getIp(), request.getPort(), finalResult);
                if (CommandValidator.validateEditCommand(request.getData())) {
                    eventSender.notifyAboutEvent(new EventDataDto(EventType.UPDATE));
                }
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
