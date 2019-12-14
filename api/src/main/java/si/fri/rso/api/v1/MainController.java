package si.fri.rso.api.v1;


import si.fri.rso.lib.responses.ResponseDTO;

public class MainController {

    public ResponseDTO responseOk(String message, Object data) {
        return new ResponseDTO(200, message, data);
    }

    public ResponseDTO responseError(Integer status, String message) {
        return new ResponseDTO(status, message, new Object());
    }
}
