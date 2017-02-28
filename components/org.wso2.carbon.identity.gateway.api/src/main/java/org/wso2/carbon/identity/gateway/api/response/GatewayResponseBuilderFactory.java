/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.gateway.api.response;

import org.wso2.carbon.identity.common.base.handler.AbstractHandler;
import org.wso2.carbon.identity.gateway.api.exception.GatewayClientException;
import org.wso2.carbon.identity.gateway.api.exception.GatewayRuntimeException;
import org.wso2.carbon.identity.gateway.api.exception.GatewayServerException;
import org.wso2.carbon.identity.gateway.common.util.Constants;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

public class GatewayResponseBuilderFactory extends AbstractHandler {

    public boolean canHandle(GatewayResponse gatewayResponse) {
        return false;
    }

    public boolean canHandle(GatewayClientException exception) {
        return false;
    }

    public boolean canHandle(GatewayServerException exception) {
        return true;
    }

    @Override
    public int getPriority() {
        return 100 ;
    }

    public Response.ResponseBuilder createBuilder(GatewayResponse gatewayResponse) {
        Response.ResponseBuilder builder = Response.noContent();
        createBuilder(builder, gatewayResponse);
        return builder;
    }

    public void createBuilder(Response.ResponseBuilder builder, GatewayResponse gatewayResponse) {
        javax.ws.rs.core.Cookie cookie1 = new javax.ws.rs.core.Cookie(Constants.GATEWAY_COOKIE, gatewayResponse
                .getSessionKey(), "/", "");

        NewCookie newCookie = new NewCookie(Constants.GATEWAY_COOKIE , gatewayResponse.getSessionKey(), "/", null,
                                            "GatewayCookie", 6000, true, true);
        builder.cookie(newCookie);
    }

    public Response.ResponseBuilder handleException(GatewayServerException exception) {

        Response.ResponseBuilder builder = Response.noContent();
        builder.status(500);
        builder.entity(exception.getMessage());
        return builder;
    }

    public Response.ResponseBuilder handleException(GatewayClientException exception) {

        Response.ResponseBuilder builder = Response.noContent();
        builder.status(500);
        builder.entity(exception.getMessage());
        return builder;
    }

    public Response.ResponseBuilder handleException(GatewayRuntimeException exception) {

        Response.ResponseBuilder builder = Response.noContent();
        builder.status(500);
        builder.entity(exception.getMessage());
        return builder;
    }

    public Response.ResponseBuilder handleException(RuntimeException exception) {

        Response.ResponseBuilder builder = Response.noContent();
        builder.status(500);
        builder.entity(exception.getMessage());
        return builder;
    }
}
