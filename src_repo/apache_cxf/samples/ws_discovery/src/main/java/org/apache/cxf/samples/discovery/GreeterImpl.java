/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.cxf.samples.discovery;

import javax.jws.WebService;


import org.apache.cxf.hello_world.discovery.Greeter;
import org.apache.cxf.hello_world.discovery.PingMeFault;

/**
 *
 */
@WebService(endpointInterface = "org.apache.cxf.hello_world.discovery.Greeter",
    wsdlLocation = "classpath:/org/apache/cxf/hello_world/discovery/hello_world.wsdl",
    targetNamespace = "http://cxf.apache.org/hello_world/discovery",
    serviceName = "GreeterService",
    portName = "GreeterPort",
    name = "GreeterPort")
public class GreeterImpl implements Greeter {
    int port;
    public GreeterImpl(int port) {
        this.port = port;
    }

    public void pingMe() throws PingMeFault {

    }

    public String sayHi() {
        return null;
    }

    public void greetMeOneWay(String requestType) {

    }

    public String greetMe(String requestType) {
        return "Hello " + requestType + " from port " + port + "!";
    }

}