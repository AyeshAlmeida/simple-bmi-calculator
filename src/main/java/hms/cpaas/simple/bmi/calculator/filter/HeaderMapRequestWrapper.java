/*
 * (C) Copyright 2010- 2019 hSenid Mobile Solutions (Pvt) Limited.
 * All Rights Reserved.
 *
 * These materials are unpublished, proprietary, confidential source code of
 * hSenid Mobile Solutions (Pvt) Limited and constitute a TRADE SECRET
 * of hSenid Mobile Solutions (Pvt) Limited.
 *
 * hSenid Mobile Solutions (Pvt) Limited retains all title to and intellectual
 * property rights in these materials.
 */

package hms.cpaas.simple.bmi.calculator.filter;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.support.ServerRequestWrapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HeaderMapRequestWrapper extends ServerRequestWrapper {

    private final CustomHeaderWrapper headerWrapper;

    public HeaderMapRequestWrapper(ServerRequest delegate) {
        super(delegate);
        this.headerWrapper = new CustomHeaderWrapper(delegate.headers());
    }

    @Override
    public Headers headers() {
        return headerWrapper;
    }

    /**
     * add a header with given name and value
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        headerWrapper.addHeader(name, value);
    }


    public static class CustomHeaderWrapper extends HeadersWrapper {
        private Map<String, List<String>> headerMap = new HashMap<>();

        public CustomHeaderWrapper(Headers headers) {
            super(headers);
        }

        @Override
        public List<String> header(String headerName) {
            List<String> hValue = super.header(headerName);
            if (headerMap.containsKey(headerName)) {
                hValue = headerMap.get(headerName);
            }
            return hValue;
        }


        /**
         * add a header with given name and value
         *
         * @param name
         * @param value
         */
        public void addHeader(String name, String value) {
            headerMap.put(name, Arrays.asList(value));
        }
    }
}
