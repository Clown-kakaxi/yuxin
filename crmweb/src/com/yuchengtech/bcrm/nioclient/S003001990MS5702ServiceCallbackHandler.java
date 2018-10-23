package com.yuchengtech.bcrm.nioclient;
//
///**
// * S003001990MS5702ServiceCallbackHandler.java
// *
// * This file was auto-generated from WSDL
// * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
// */
//
//    package com.yuchengtech.bcrm.webservice;
//
//    /**
//     *  S003001990MS5702ServiceCallbackHandler Callback class, Users can extend this class and implement
//     *  their own receiveResult and receiveError methods.
//     */
//    public abstract class S003001990MS5702ServiceCallbackHandler{
//
//
//
//    protected Object clientData;
//
//    /**
//    * User can pass in any object that needs to be accessed once the NonBlocking
//    * Web service call is finished and appropriate method of this CallBack is called.
//    * @param clientData Object mechanism by which the user can pass in user data
//    * that will be avilable at the time this callback is called.
//    */
//    public S003001990MS5702ServiceCallbackHandler(Object clientData){
//        this.clientData = clientData;
//    }
//
//    /**
//    * Please use this constructor if you don't want to set any clientData
//    */
//    public S003001990MS5702ServiceCallbackHandler(){
//        this.clientData = null;
//    }
//
//    /**
//     * Get the client data
//     */
//
//     public Object getClientData() {
//        return clientData;
//     }
//
//        
//           /**
//            * auto generated Axis2 call back method for s003001990MS5702 method
//            * override this method for handling normal response from s003001990MS5702 operation
//            */
//           public void receiveResults003001990MS5702(
//                    S003001990MS5702ServiceStub.S003001990MS5702Response result
//                        ) {
//           }
//
//          /**
//           * auto generated Axis2 Error handler
//           * override this method for handling error response from s003001990MS5702 operation
//           */
//            public void receiveErrors003001990MS5702(java.lang.Exception e) {
//            }
//                
//
//
//    }
//    