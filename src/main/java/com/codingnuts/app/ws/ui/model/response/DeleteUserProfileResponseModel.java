package com.codingnuts.app.ws.ui.model.response;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeleteUserProfileResponseModel {
    private RequestOperation requestOperation;
    private ResponseStatus responseStatus;

    public RequestOperation getRequestOperation() {
        return requestOperation;
    }

    public void setRequestOperation(RequestOperation requestOperation) {
        this.requestOperation = requestOperation;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
