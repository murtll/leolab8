package lab7.client.Smth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lab7.client.Entity.FuelType;
import lab7.client.Entity.Vehicle;
import lab7.client.Network.*;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * class responsible for implementing the commands of my project
 *
 * @author Nagorny Leonid
 */

public class ServerCommander {

    private final DatagramClient client;
    private final ObjectMapper mapper;

    public ServerCommander(DatagramClient client) {
        this.client = client;
        this.mapper = new ObjectMapper();
        this.mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        this.mapper.findAndRegisterModules();
    }

    public ServerResponseDto add(Vehicle v) throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("add", v);
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto show() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("show");
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto help() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("help");
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto login() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("login");
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto register() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("register");
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto clear() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("clear");
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto removeById(Long id) throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("remove_by_id", id);
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto shuffle() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("shuffle");
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto info() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("info");
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto countLessThanFuelType(FuelType fuelType) throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("count_less_than_fuel_type", fuelType);
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto countByEnginePower(int enginePower) throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("count_by_engine_power", enginePower);
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto groupCountingByCreationDate() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("group_counting_by_creation_date");
        return sendDtoAndReceiveResponse(clientData);
    }


    public ServerResponseDto removeLast() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("remove_last");
        return sendDtoAndReceiveResponse(clientData);
    }

    public ServerResponseDto updateId(long id, Vehicle v) throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("update", v, id);
        return sendDtoAndReceiveResponse(clientData);
    }

    private ServerResponseDto sendDtoAndReceiveResponse(ClientDataDto clientData) throws IOException, InterruptedException {
        ClientRequestDto requestDto = new ClientRequestDto(clientData, CredentialHandler.getCredentials());
        String serializedData = mapper.writeValueAsString(requestDto);
        client.send(serializedData);
        String serverResponseString = client.receiveResponse();
        return mapper.readValue(serverResponseString, ServerResponseDto.class);
    }

    public ServerResponseDto showMy() throws IOException, InterruptedException {
        ClientDataDto clientData = new ClientDataDto("show_my");
        return sendDtoAndReceiveResponse(clientData);
    }
}
