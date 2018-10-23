package com.yuchengtech.bcrm.nioclient;
//
///**
// * S003001990MS5702ServiceStub.java
// *
// * This file was auto-generated from WSDL
// * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
// */
//        package com.yuchengtech.bcrm.webservice;
//
//        
//
//        /*
//        *  S003001990MS5702ServiceStub java implementation
//        */
//
//        
//        public class S003001990MS5702ServiceStub extends org.apache.axis2.client.Stub
//        {
//        protected org.apache.axis2.description.AxisOperation[] _operations;
//
//        //hashmaps to keep the fault mapping
//        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
//        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
//        private java.util.HashMap faultMessageMap = new java.util.HashMap();
//
//        private static int counter = 0;
//
//        private static synchronized java.lang.String getUniqueSuffix(){
//            // reset the counter if it is greater than 99999
//            if (counter > 99999){
//                counter = 0;
//            }
//            counter = counter + 1; 
//            return java.lang.Long.toString(java.lang.System.currentTimeMillis()) + "_" + counter;
//        }
//
//    
//    private void populateAxisService() throws org.apache.axis2.AxisFault {
//
//     //creating the Service with a unique name
//     _service = new org.apache.axis2.description.AxisService("S003001990MS5702Service" + getUniqueSuffix());
//     addAnonymousOperations();
//
//        //creating the operations
//        org.apache.axis2.description.AxisOperation __operation;
//
//        _operations = new org.apache.axis2.description.AxisOperation[1];
//        
//                   __operation = new org.apache.axis2.description.OutInAxisOperation();
//                
//
//            __operation.setName(new javax.xml.namespace.QName("http://www.adtec.com.cn", "s003001990MS5702"));
//	    _service.addOperation(__operation);
//	    
//
//	    
//	    
//            _operations[0]=__operation;
//            
//        
//        }
//
//    //populates the faults
//    private void populateFaults(){
//         
//
//
//    }
//
//    /**
//      *Constructor that takes in a configContext
//      */
//
//    public S003001990MS5702ServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
//       java.lang.String targetEndpoint)
//       throws org.apache.axis2.AxisFault {
//         this(configurationContext,targetEndpoint,false);
//   }
//
//
//   /**
//     * Constructor that takes in a configContext  and useseperate listner
//     */
//   public S003001990MS5702ServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
//        java.lang.String targetEndpoint, boolean useSeparateListener)
//        throws org.apache.axis2.AxisFault {
//         //To populate AxisService
//         populateAxisService();
//         populateFaults();
//
//        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
//        
//	
//        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
//                targetEndpoint));
//        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
//        
//    
//    }
//
//    /**
//     * Default Constructor
//     */
//    public S003001990MS5702ServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
//        
//                    this(configurationContext,"http://118.63.1.11:8010/S003001990MS5702" );
//                
//    }
//
//    /**
//     * Default Constructor
//     */
//    public S003001990MS5702ServiceStub() throws org.apache.axis2.AxisFault {
//        
//                    this("http://118.63.1.11:8010/S003001990MS5702" );
//                
//    }
//
//    /**
//     * Constructor taking the target endpoint
//     */
//    public S003001990MS5702ServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
//        this(null,targetEndpoint);
//    }
//
//
//
//        
//                    /**
//                     * Auto generated method signature
//                     * 
//                     * @see cn.com.adtec.www.S003001990MS5702Service#s003001990MS5702
//                     * @param s003001990MS57020
//                    
//                     */
//
//                    
//
//                            public  S003001990MS5702ServiceStub.S003001990MS5702Response s003001990MS5702(
//
//                            S003001990MS5702ServiceStub.S003001990MS5702 s003001990MS57020)
//                        
//
//                    throws java.rmi.RemoteException
//                    
//                    {
//              org.apache.axis2.context.MessageContext _messageContext = null;
//              try{
//               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
//              _operationClient.getOptions().setAction("http://118.63.1.11:8010/S003001990MS5702");
//              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
//
//              
//              
//                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
//              
//
//              // create a message context
//              _messageContext = new org.apache.axis2.context.MessageContext();
//
//              
//
//              // create SOAP envelope with that payload
//              org.apache.axiom.soap.SOAPEnvelope env = null;
//                    
//                                                    
//                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
//                                                    s003001990MS57020,
//                                                    optimizeContent(new javax.xml.namespace.QName("http://www.adtec.com.cn",
//                                                    "s003001990MS5702")), new javax.xml.namespace.QName("http://www.adtec.com.cn",
//                                                    "s003001990MS5702"));
//                                                
//        //adding SOAP soap_headers
//         _serviceClient.addHeadersToEnvelope(env);
//        // set the message context with that soap envelope
//        _messageContext.setEnvelope(env);
//
//        // add the message contxt to the operation client
//        _operationClient.addMessageContext(_messageContext);
//
//        //execute the operation client
//        _operationClient.execute(true);
//
//         
//               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
//                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
//                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
//                
//                
//                                java.lang.Object object = fromOM(
//                                             _returnEnv.getBody().getFirstElement() ,
//                                            S003001990MS5702ServiceStub.S003001990MS5702Response.class,
//                                              getEnvelopeNamespaces(_returnEnv));
//
//                               
//                                        return (S003001990MS5702ServiceStub.S003001990MS5702Response)object;
//                                   
//         }catch(org.apache.axis2.AxisFault f){
//
//            org.apache.axiom.om.OMElement faultElt = f.getDetail();
//            if (faultElt!=null){
//                if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"S003001990MS5702"))){
//                    //make the fault by reflection
//                    try{
//                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"S003001990MS5702"));
//                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
//                        java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
//                        java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
//                        //message class
//                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"S003001990MS5702"));
//                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
//                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
//                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
//                                   new java.lang.Class[]{messageClass});
//                        m.invoke(ex,new java.lang.Object[]{messageObject});
//                        
//
//                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
//                    }catch(java.lang.ClassCastException e){
//                       // we cannot intantiate the class - throw the original Axis fault
//                        throw f;
//                    } catch (java.lang.ClassNotFoundException e) {
//                        // we cannot intantiate the class - throw the original Axis fault
//                        throw f;
//                    }catch (java.lang.NoSuchMethodException e) {
//                        // we cannot intantiate the class - throw the original Axis fault
//                        throw f;
//                    } catch (java.lang.reflect.InvocationTargetException e) {
//                        // we cannot intantiate the class - throw the original Axis fault
//                        throw f;
//                    }  catch (java.lang.IllegalAccessException e) {
//                        // we cannot intantiate the class - throw the original Axis fault
//                        throw f;
//                    }   catch (java.lang.InstantiationException e) {
//                        // we cannot intantiate the class - throw the original Axis fault
//                        throw f;
//                    }
//                }else{
//                    throw f;
//                }
//            }else{
//                throw f;
//            }
//            } finally {
//                if (_messageContext.getTransportOut() != null) {
//                      _messageContext.getTransportOut().getSender().cleanup(_messageContext);
//                }
//            }
//        }
//            
//                /**
//                * Auto generated method signature for Asynchronous Invocations
//                * 
//                * @see cn.com.adtec.www.S003001990MS5702Service#starts003001990MS5702
//                    * @param s003001990MS57020
//                
//                */
//                public  void starts003001990MS5702(
//
//                 S003001990MS5702ServiceStub.S003001990MS5702 s003001990MS57020,
//
//                  final S003001990MS5702ServiceCallbackHandler callback)
//
//                throws java.rmi.RemoteException{
//
//              org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
//             _operationClient.getOptions().setAction("http://118.63.1.11:8010/S003001990MS5702");
//             _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);
//
//              
//              
//                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
//              
//
//
//              // create SOAP envelope with that payload
//              org.apache.axiom.soap.SOAPEnvelope env=null;
//              final org.apache.axis2.context.MessageContext _messageContext = new org.apache.axis2.context.MessageContext();
//
//                    
//                                    //Style is Doc.
//                                    
//                                                    
//                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
//                                                    s003001990MS57020,
//                                                    optimizeContent(new javax.xml.namespace.QName("http://www.adtec.com.cn",
//                                                    "s003001990MS5702")), new javax.xml.namespace.QName("http://www.adtec.com.cn",
//                                                    "s003001990MS5702"));
//                                                
//        // adding SOAP soap_headers
//         _serviceClient.addHeadersToEnvelope(env);
//        // create message context with that soap envelope
//        _messageContext.setEnvelope(env);
//
//        // add the message context to the operation client
//        _operationClient.addMessageContext(_messageContext);
//
//
//                    
//                        _operationClient.setCallback(new org.apache.axis2.client.async.AxisCallback() {
//                            public void onMessage(org.apache.axis2.context.MessageContext resultContext) {
//                            try {
//                                org.apache.axiom.soap.SOAPEnvelope resultEnv = resultContext.getEnvelope();
//                                
//                                        java.lang.Object object = fromOM(resultEnv.getBody().getFirstElement(),
//                                                                         S003001990MS5702ServiceStub.S003001990MS5702Response.class,
//                                                                         getEnvelopeNamespaces(resultEnv));
//                                        callback.receiveResults003001990MS5702(
//                                        (S003001990MS5702ServiceStub.S003001990MS5702Response)object);
//                                        
//                            } catch (org.apache.axis2.AxisFault e) {
//                                callback.receiveErrors003001990MS5702(e);
//                            }
//                            }
//
//                            public void onError(java.lang.Exception error) {
//								if (error instanceof org.apache.axis2.AxisFault) {
//									org.apache.axis2.AxisFault f = (org.apache.axis2.AxisFault) error;
//									org.apache.axiom.om.OMElement faultElt = f.getDetail();
//									if (faultElt!=null){
//										if (faultExceptionNameMap.containsKey(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"S003001990MS5702"))){
//											//make the fault by reflection
//											try{
//													java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"S003001990MS5702"));
//													java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
//													java.lang.reflect.Constructor constructor = exceptionClass.getConstructor(String.class);
//                                                    java.lang.Exception ex = (java.lang.Exception) constructor.newInstance(f.getMessage());
//													//message class
//													java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(new org.apache.axis2.client.FaultMapKey(faultElt.getQName(),"S003001990MS5702"));
//														java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
//													java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
//													java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
//															new java.lang.Class[]{messageClass});
//													m.invoke(ex,new java.lang.Object[]{messageObject});
//													
//					
//										            callback.receiveErrors003001990MS5702(new java.rmi.RemoteException(ex.getMessage(), ex));
//                                            } catch(java.lang.ClassCastException e){
//                                                // we cannot intantiate the class - throw the original Axis fault
//                                                callback.receiveErrors003001990MS5702(f);
//                                            } catch (java.lang.ClassNotFoundException e) {
//                                                // we cannot intantiate the class - throw the original Axis fault
//                                                callback.receiveErrors003001990MS5702(f);
//                                            } catch (java.lang.NoSuchMethodException e) {
//                                                // we cannot intantiate the class - throw the original Axis fault
//                                                callback.receiveErrors003001990MS5702(f);
//                                            } catch (java.lang.reflect.InvocationTargetException e) {
//                                                // we cannot intantiate the class - throw the original Axis fault
//                                                callback.receiveErrors003001990MS5702(f);
//                                            } catch (java.lang.IllegalAccessException e) {
//                                                // we cannot intantiate the class - throw the original Axis fault
//                                                callback.receiveErrors003001990MS5702(f);
//                                            } catch (java.lang.InstantiationException e) {
//                                                // we cannot intantiate the class - throw the original Axis fault
//                                                callback.receiveErrors003001990MS5702(f);
//                                            } catch (org.apache.axis2.AxisFault e) {
//                                                // we cannot intantiate the class - throw the original Axis fault
//                                                callback.receiveErrors003001990MS5702(f);
//                                            }
//									    } else {
//										    callback.receiveErrors003001990MS5702(f);
//									    }
//									} else {
//									    callback.receiveErrors003001990MS5702(f);
//									}
//								} else {
//								    callback.receiveErrors003001990MS5702(error);
//								}
//                            }
//
//                            public void onFault(org.apache.axis2.context.MessageContext faultContext) {
//                                org.apache.axis2.AxisFault fault = org.apache.axis2.util.Utils.getInboundFaultFromMessageContext(faultContext);
//                                onError(fault);
//                            }
//
//                            public void onComplete() {
//                                try {
//                                    _messageContext.getTransportOut().getSender().cleanup(_messageContext);
//                                } catch (org.apache.axis2.AxisFault axisFault) {
//                                    callback.receiveErrors003001990MS5702(axisFault);
//                                }
//                            }
//                });
//                        
//
//          org.apache.axis2.util.CallbackReceiver _callbackReceiver = null;
//        if ( _operations[0].getMessageReceiver()==null &&  _operationClient.getOptions().isUseSeparateListener()) {
//           _callbackReceiver = new org.apache.axis2.util.CallbackReceiver();
//          _operations[0].setMessageReceiver(
//                    _callbackReceiver);
//        }
//
//           //execute the operation client
//           _operationClient.execute(false);
//
//                    }
//                
//
//
//       /**
//        *  A utility method that copies the namepaces from the SOAPEnvelope
//        */
//       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
//        java.util.Map returnMap = new java.util.HashMap();
//        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
//        while (namespaceIterator.hasNext()) {
//            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
//            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
//        }
//       return returnMap;
//    }
//
//    
//    
//    private javax.xml.namespace.QName[] opNameArray = null;
//    private boolean optimizeContent(javax.xml.namespace.QName opName) {
//        
//
//        if (opNameArray == null) {
//            return false;
//        }
//        for (int i = 0; i < opNameArray.length; i++) {
//            if (opName.equals(opNameArray[i])) {
//                return true;   
//            }
//        }
//        return false;
//    }
//     //http://118.63.1.11:8010/S003001990MS5702
//        public static class S003001990MS5702Response
//        implements org.apache.axis2.databinding.ADBBean{
//        
//                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
//                "http://www.adtec.com.cn",
//                "S003001990MS5702Response",
//                "ns1");
//
//            
//
//                        /**
//                        * field for ResponseHeader
//                        */
//
//                        
//                                    protected ResponseHeader localResponseHeader ;
//                                
//
//                           /**
//                           * Auto generated getter method
//                           * @return ResponseHeader
//                           */
//                           public  ResponseHeader getResponseHeader(){
//                               return localResponseHeader;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ResponseHeader
//                               */
//                               public void setResponseHeader(ResponseHeader param){
//                            
//                                            this.localResponseHeader=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ResponseBody
//                        */
//
//                        
//                                    protected ResponseBody localResponseBody ;
//                                
//
//                           /**
//                           * Auto generated getter method
//                           * @return ResponseBody
//                           */
//                           public  ResponseBody getResponseBody(){
//                               return localResponseBody;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ResponseBody
//                               */
//                               public void setResponseBody(ResponseBody param){
//                            
//                                            this.localResponseBody=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Fault
//                        */
//
//                        
//                                    protected Fault localFault ;
//                                
//
//                           /**
//                           * Auto generated getter method
//                           * @return Fault
//                           */
//                           public  Fault getFault(){
//                               return localFault;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Fault
//                               */
//                               public void setFault(Fault param){
//                            
//                                            this.localFault=param;
//                                    
//
//                               }
//                            
//
//     
//     
//        /**
//        *
//        * @param parentQName
//        * @param factory
//        * @return org.apache.axiom.om.OMElement
//        */
//       public org.apache.axiom.om.OMElement getOMElement (
//               final javax.xml.namespace.QName parentQName,
//               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{
//
//
//        
//               org.apache.axiom.om.OMDataSource dataSource =
//                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME);
//               return factory.createOMElement(dataSource,MY_QNAME);
//            
//        }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter)
//                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//                           serialize(parentQName,xmlWriter,false);
//         }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                               javax.xml.stream.XMLStreamWriter xmlWriter,
//                               boolean serializeType)
//            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//            
//                
//
//
//                java.lang.String prefix = null;
//                java.lang.String namespace = null;
//                
//
//                    prefix = parentQName.getPrefix();
//                    namespace = parentQName.getNamespaceURI();
//                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
//                
//                  if (serializeType){
//               
//
//                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adtec.com.cn");
//                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           namespacePrefix+":S003001990MS5702Response",
//                           xmlWriter);
//                   } else {
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           "S003001990MS5702Response",
//                           xmlWriter);
//                   }
//
//               
//                   }
//               
//                                    if (localResponseHeader==null){
//
//                                        writeStartElement(null, "", "ResponseHeader", xmlWriter);
//
//                                       // write the nil attribute
//                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
//                                      xmlWriter.writeEndElement();
//                                    }else{
//                                     localResponseHeader.serialize(new javax.xml.namespace.QName("","ResponseHeader"),
//                                        xmlWriter);
//                                    }
//                                
//                                    if (localResponseBody==null){
//
//                                        writeStartElement(null, "", "ResponseBody", xmlWriter);
//
//                                       // write the nil attribute
//                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
//                                      xmlWriter.writeEndElement();
//                                    }else{
//                                     localResponseBody.serialize(new javax.xml.namespace.QName("","ResponseBody"),
//                                        xmlWriter);
//                                    }
//                                
//                                    if (localFault==null){
//
//                                        writeStartElement(null, "", "Fault", xmlWriter);
//
//                                       // write the nil attribute
//                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
//                                      xmlWriter.writeEndElement();
//                                    }else{
//                                     localFault.serialize(new javax.xml.namespace.QName("","Fault"),
//                                        xmlWriter);
//                                    }
//                                
//                    xmlWriter.writeEndElement();
//               
//
//        }
//
//        private static java.lang.String generatePrefix(java.lang.String namespace) {
//            if(namespace.equals("http://www.adtec.com.cn")){
//                return "ns1";
//            }
//            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//        }
//
//        /**
//         * Utility method to write an element start tag.
//         */
//        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
//            if (writerPrefix != null) {
//                xmlWriter.writeStartElement(namespace, localPart);
//            } else {
//                if (namespace.length() == 0) {
//                    prefix = "";
//                } else if (prefix == null) {
//                    prefix = generatePrefix(namespace);
//                }
//
//                xmlWriter.writeStartElement(prefix, localPart, namespace);
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//        }
//        
//        /**
//         * Util method to write an attribute with the ns prefix
//         */
//        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (xmlWriter.getPrefix(namespace) == null) {
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            xmlWriter.writeAttribute(namespace,attName,attValue);
//        }
//
//        /**
//         * Util method to write an attribute without the ns prefix
//         */
//        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (namespace.equals("")) {
//                xmlWriter.writeAttribute(attName,attValue);
//            } else {
//                registerPrefix(xmlWriter, namespace);
//                xmlWriter.writeAttribute(namespace,attName,attValue);
//            }
//        }
//
//
//           /**
//             * Util method to write an attribute without the ns prefix
//             */
//            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
//                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//                java.lang.String attributeNamespace = qname.getNamespaceURI();
//                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
//                if (attributePrefix == null) {
//                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
//                }
//                java.lang.String attributeValue;
//                if (attributePrefix.trim().length() > 0) {
//                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
//                } else {
//                    attributeValue = qname.getLocalPart();
//                }
//
//                if (namespace.equals("")) {
//                    xmlWriter.writeAttribute(attName, attributeValue);
//                } else {
//                    registerPrefix(xmlWriter, namespace);
//                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
//                }
//            }
//        /**
//         *  method to handle Qnames
//         */
//
//        private void writeQName(javax.xml.namespace.QName qname,
//                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String namespaceURI = qname.getNamespaceURI();
//            if (namespaceURI != null) {
//                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
//                if (prefix == null) {
//                    prefix = generatePrefix(namespaceURI);
//                    xmlWriter.writeNamespace(prefix, namespaceURI);
//                    xmlWriter.setPrefix(prefix,namespaceURI);
//                }
//
//                if (prefix.trim().length() > 0){
//                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                } else {
//                    // i.e this is the default namespace
//                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                }
//
//            } else {
//                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//            }
//        }
//
//        private void writeQNames(javax.xml.namespace.QName[] qnames,
//                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//            if (qnames != null) {
//                // we have to store this data until last moment since it is not possible to write any
//                // namespace data after writing the charactor data
//                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
//                java.lang.String namespaceURI = null;
//                java.lang.String prefix = null;
//
//                for (int i = 0; i < qnames.length; i++) {
//                    if (i > 0) {
//                        stringToWrite.append(" ");
//                    }
//                    namespaceURI = qnames[i].getNamespaceURI();
//                    if (namespaceURI != null) {
//                        prefix = xmlWriter.getPrefix(namespaceURI);
//                        if ((prefix == null) || (prefix.length() == 0)) {
//                            prefix = generatePrefix(namespaceURI);
//                            xmlWriter.writeNamespace(prefix, namespaceURI);
//                            xmlWriter.setPrefix(prefix,namespaceURI);
//                        }
//
//                        if (prefix.trim().length() > 0){
//                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        } else {
//                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        }
//                    } else {
//                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                    }
//                }
//                xmlWriter.writeCharacters(stringToWrite.toString());
//            }
//
//        }
//
//
//        /**
//         * Register a namespace prefix
//         */
//        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
//            java.lang.String prefix = xmlWriter.getPrefix(namespace);
//            if (prefix == null) {
//                prefix = generatePrefix(namespace);
//                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
//                while (true) {
//                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
//                    if (uri == null || uri.length() == 0) {
//                        break;
//                    }
//                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//                }
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            return prefix;
//        }
//
//
//  
//        /**
//        * databinding method to get an XML representation of this object
//        *
//        */
//        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
//                    throws org.apache.axis2.databinding.ADBException{
//
//
//        
//                 java.util.ArrayList elementList = new java.util.ArrayList();
//                 java.util.ArrayList attribList = new java.util.ArrayList();
//
//                
//                            elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ResponseHeader"));
//                            
//                            
//                                    elementList.add(localResponseHeader==null?null:
//                                    localResponseHeader);
//                                
//                            elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ResponseBody"));
//                            
//                            
//                                    elementList.add(localResponseBody==null?null:
//                                    localResponseBody);
//                                
//                            elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Fault"));
//                            
//                            
//                                    elementList.add(localFault==null?null:
//                                    localFault);
//                                
//
//                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
//            
//            
//
//        }
//
//  
//
//     /**
//      *  Factory class that keeps the parse method
//      */
//    public static class Factory{
//
//        
//        
//
//        /**
//        * static method to create the object
//        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
//        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
//        * Postcondition: If this object is an element, the reader is positioned at its end element
//        *                If this object is a complex type, the reader is positioned at the end element of its outer element
//        */
//        public static S003001990MS5702Response parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
//            S003001990MS5702Response object =
//                new S003001990MS5702Response();
//
//            int event;
//            java.lang.String nillableValue = null;
//            java.lang.String prefix ="";
//            java.lang.String namespaceuri ="";
//            try {
//                
//                while (!reader.isStartElement() && !reader.isEndElement())
//                    reader.next();
//
//                
//                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
//                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
//                        "type");
//                  if (fullTypeName!=null){
//                    java.lang.String nsPrefix = null;
//                    if (fullTypeName.indexOf(":") > -1){
//                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
//                    }
//                    nsPrefix = nsPrefix==null?"":nsPrefix;
//
//                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
//                    
//                            if (!"S003001990MS5702Response".equals(type)){
//                                //find namespace for the prefix
//                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
//                                return (S003001990MS5702Response)ExtensionMapper.getTypeObject(
//                                     nsUri,type,reader);
//                              }
//                        
//
//                  }
//                
//
//                }
//
//                
//
//                
//                // Note all attributes that were handled. Used to differ normal attributes
//                // from anyAttributes.
//                java.util.Vector handledAttributes = new java.util.Vector();
//                
//
//                
//                    
//                    reader.next();
//                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ResponseHeader").equals(reader.getName())){
//                                
//                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                          object.setResponseHeader(null);
//                                          reader.next();
//                                            
//                                            reader.next();
//                                          
//                                      }else{
//                                    
//                                                object.setResponseHeader(ResponseHeader.Factory.parse(reader));
//                                              
//                                        reader.next();
//                                    }
//                              }  // End of if for expected property start element
//                                
//                                else{
//                                    // A start element we are not expecting indicates an invalid parameter was passed
//                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                                }
//                            
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ResponseBody").equals(reader.getName())){
//                                
//                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                          object.setResponseBody(null);
//                                          reader.next();
//                                            
//                                            reader.next();
//                                          
//                                      }else{
//                                    
//                                                object.setResponseBody(ResponseBody.Factory.parse(reader));
//                                              
//                                        reader.next();
//                                    }
//                              }  // End of if for expected property start element
//                                
//                                else{
//                                    // A start element we are not expecting indicates an invalid parameter was passed
//                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                                }
//                            
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Fault").equals(reader.getName())){
//                                
//                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                          object.setFault(null);
//                                          reader.next();
//                                            
//                                            reader.next();
//                                          
//                                      }else{
//                                    
//                                                object.setFault(Fault.Factory.parse(reader));
//                                              
//                                        reader.next();
//                                    }
//                              }  // End of if for expected property start element
//                                
//                                else{
//                                    // A start element we are not expecting indicates an invalid parameter was passed
//                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                                }
//                              
//                            while (!reader.isStartElement() && !reader.isEndElement())
//                                reader.next();
//                            
//                                if (reader.isStartElement())
//                                // A start element we are not expecting indicates a trailing invalid property
//                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                            
//
//
//
//            } catch (javax.xml.stream.XMLStreamException e) {
//                throw new java.lang.Exception(e);
//            }
//
//            return object;
//        }
//
//        }//end of factory class
//
//        
//
//        }
//           
//    
//        public static class ExtensionMapper{
//
//          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
//                                                       java.lang.String typeName,
//                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
//
//              
//                  if (
//                  "http://www.adtec.com.cn".equals(namespaceURI) &&
//                  "Fault".equals(typeName)){
//                   
//                            return  Fault.Factory.parse(reader);
//                        
//
//                  }
//
//              
//                  if (
//                  "http://www.adtec.com.cn".equals(namespaceURI) &&
//                  "Detail".equals(typeName)){
//                   
//                            return  Detail.Factory.parse(reader);
//                        
//
//                  }
//
//              
//                  if (
//                  "http://www.adtec.com.cn".equals(namespaceURI) &&
//                  "RequestBody".equals(typeName)){
//                   
//                            return  RequestBody.Factory.parse(reader);
//                        
//
//                  }
//
//              
//                  if (
//                  "http://www.adtec.com.cn".equals(namespaceURI) &&
//                  "RequestHeader".equals(typeName)){
//                   
//                            return  RequestHeader.Factory.parse(reader);
//                        
//
//                  }
//
//              
//                  if (
//                  "http://www.adtec.com.cn".equals(namespaceURI) &&
//                  "ResponseBody".equals(typeName)){
//                   
//                            return  ResponseBody.Factory.parse(reader);
//                        
//
//                  }
//
//              
//                  if (
//                  "http://www.adtec.com.cn".equals(namespaceURI) &&
//                  "ResponseHeader".equals(typeName)){
//                   
//                            return  ResponseHeader.Factory.parse(reader);
//                        
//
//                  }
//
//              
//             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
//          }
//
//        }
//    
//        public static class Fault
//        implements org.apache.axis2.databinding.ADBBean{
//        /* This type was generated from the piece of schema that had
//                name = Fault
//                Namespace URI = http://www.adtec.com.cn
//                Namespace Prefix = ns1
//                */
//            
//
//                        /**
//                        * field for FaultCode
//                        */
//
//                        
//                                    protected java.lang.String localFaultCode ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localFaultCodeTracker = false ;
//
//                           public boolean isFaultCodeSpecified(){
//                               return localFaultCodeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getFaultCode(){
//                               return localFaultCode;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param FaultCode
//                               */
//                               public void setFaultCode(java.lang.String param){
//                            localFaultCodeTracker = param != null;
//                                   
//                                            this.localFaultCode=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for FaultString
//                        */
//
//                        
//                                    protected java.lang.String localFaultString ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localFaultStringTracker = false ;
//
//                           public boolean isFaultStringSpecified(){
//                               return localFaultStringTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getFaultString(){
//                               return localFaultString;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param FaultString
//                               */
//                               public void setFaultString(java.lang.String param){
//                            localFaultStringTracker = param != null;
//                                   
//                                            this.localFaultString=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Detail
//                        */
//
//                        
//                                    protected Detail localDetail ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localDetailTracker = false ;
//
//                           public boolean isDetailSpecified(){
//                               return localDetailTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return Detail
//                           */
//                           public  Detail getDetail(){
//                               return localDetail;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Detail
//                               */
//                               public void setDetail(Detail param){
//                            localDetailTracker = param != null;
//                                   
//                                            this.localDetail=param;
//                                    
//
//                               }
//                            
//
//     
//     
//        /**
//        *
//        * @param parentQName
//        * @param factory
//        * @return org.apache.axiom.om.OMElement
//        */
//       public org.apache.axiom.om.OMElement getOMElement (
//               final javax.xml.namespace.QName parentQName,
//               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{
//
//
//        
//               org.apache.axiom.om.OMDataSource dataSource =
//                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
//               return factory.createOMElement(dataSource,parentQName);
//            
//        }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter)
//                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//                           serialize(parentQName,xmlWriter,false);
//         }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                               javax.xml.stream.XMLStreamWriter xmlWriter,
//                               boolean serializeType)
//            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//            
//                
//
//
//                java.lang.String prefix = null;
//                java.lang.String namespace = null;
//                
//
//                    prefix = parentQName.getPrefix();
//                    namespace = parentQName.getNamespaceURI();
//                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
//                
//                  if (serializeType){
//               
//
//                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adtec.com.cn");
//                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           namespacePrefix+":Fault",
//                           xmlWriter);
//                   } else {
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           "Fault",
//                           xmlWriter);
//                   }
//
//               
//                   }
//                if (localFaultCodeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "FaultCode", xmlWriter);
//                             
//
//                                          if (localFaultCode==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("FaultCode cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localFaultCode);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localFaultStringTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "FaultString", xmlWriter);
//                             
//
//                                          if (localFaultString==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("FaultString cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localFaultString);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localDetailTracker){
//                                            if (localDetail==null){
//                                                 throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
//                                            }
//                                           localDetail.serialize(new javax.xml.namespace.QName("","Detail"),
//                                               xmlWriter);
//                                        }
//                    xmlWriter.writeEndElement();
//               
//
//        }
//
//        private static java.lang.String generatePrefix(java.lang.String namespace) {
//            if(namespace.equals("http://www.adtec.com.cn")){
//                return "ns1";
//            }
//            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//        }
//
//        /**
//         * Utility method to write an element start tag.
//         */
//        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
//            if (writerPrefix != null) {
//                xmlWriter.writeStartElement(namespace, localPart);
//            } else {
//                if (namespace.length() == 0) {
//                    prefix = "";
//                } else if (prefix == null) {
//                    prefix = generatePrefix(namespace);
//                }
//
//                xmlWriter.writeStartElement(prefix, localPart, namespace);
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//        }
//        
//        /**
//         * Util method to write an attribute with the ns prefix
//         */
//        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (xmlWriter.getPrefix(namespace) == null) {
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            xmlWriter.writeAttribute(namespace,attName,attValue);
//        }
//
//        /**
//         * Util method to write an attribute without the ns prefix
//         */
//        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (namespace.equals("")) {
//                xmlWriter.writeAttribute(attName,attValue);
//            } else {
//                registerPrefix(xmlWriter, namespace);
//                xmlWriter.writeAttribute(namespace,attName,attValue);
//            }
//        }
//
//
//           /**
//             * Util method to write an attribute without the ns prefix
//             */
//            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
//                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//                java.lang.String attributeNamespace = qname.getNamespaceURI();
//                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
//                if (attributePrefix == null) {
//                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
//                }
//                java.lang.String attributeValue;
//                if (attributePrefix.trim().length() > 0) {
//                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
//                } else {
//                    attributeValue = qname.getLocalPart();
//                }
//
//                if (namespace.equals("")) {
//                    xmlWriter.writeAttribute(attName, attributeValue);
//                } else {
//                    registerPrefix(xmlWriter, namespace);
//                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
//                }
//            }
//        /**
//         *  method to handle Qnames
//         */
//
//        private void writeQName(javax.xml.namespace.QName qname,
//                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String namespaceURI = qname.getNamespaceURI();
//            if (namespaceURI != null) {
//                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
//                if (prefix == null) {
//                    prefix = generatePrefix(namespaceURI);
//                    xmlWriter.writeNamespace(prefix, namespaceURI);
//                    xmlWriter.setPrefix(prefix,namespaceURI);
//                }
//
//                if (prefix.trim().length() > 0){
//                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                } else {
//                    // i.e this is the default namespace
//                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                }
//
//            } else {
//                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//            }
//        }
//
//        private void writeQNames(javax.xml.namespace.QName[] qnames,
//                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//            if (qnames != null) {
//                // we have to store this data until last moment since it is not possible to write any
//                // namespace data after writing the charactor data
//                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
//                java.lang.String namespaceURI = null;
//                java.lang.String prefix = null;
//
//                for (int i = 0; i < qnames.length; i++) {
//                    if (i > 0) {
//                        stringToWrite.append(" ");
//                    }
//                    namespaceURI = qnames[i].getNamespaceURI();
//                    if (namespaceURI != null) {
//                        prefix = xmlWriter.getPrefix(namespaceURI);
//                        if ((prefix == null) || (prefix.length() == 0)) {
//                            prefix = generatePrefix(namespaceURI);
//                            xmlWriter.writeNamespace(prefix, namespaceURI);
//                            xmlWriter.setPrefix(prefix,namespaceURI);
//                        }
//
//                        if (prefix.trim().length() > 0){
//                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        } else {
//                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        }
//                    } else {
//                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                    }
//                }
//                xmlWriter.writeCharacters(stringToWrite.toString());
//            }
//
//        }
//
//
//        /**
//         * Register a namespace prefix
//         */
//        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
//            java.lang.String prefix = xmlWriter.getPrefix(namespace);
//            if (prefix == null) {
//                prefix = generatePrefix(namespace);
//                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
//                while (true) {
//                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
//                    if (uri == null || uri.length() == 0) {
//                        break;
//                    }
//                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//                }
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            return prefix;
//        }
//
//
//  
//        /**
//        * databinding method to get an XML representation of this object
//        *
//        */
//        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
//                    throws org.apache.axis2.databinding.ADBException{
//
//
//        
//                 java.util.ArrayList elementList = new java.util.ArrayList();
//                 java.util.ArrayList attribList = new java.util.ArrayList();
//
//                 if (localFaultCodeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "FaultCode"));
//                                 
//                                        if (localFaultCode != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFaultCode));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("FaultCode cannot be null!!");
//                                        }
//                                    } if (localFaultStringTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "FaultString"));
//                                 
//                                        if (localFaultString != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFaultString));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("FaultString cannot be null!!");
//                                        }
//                                    } if (localDetailTracker){
//                            elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Detail"));
//                            
//                            
//                                    if (localDetail==null){
//                                         throw new org.apache.axis2.databinding.ADBException("Detail cannot be null!!");
//                                    }
//                                    elementList.add(localDetail);
//                                }
//
//                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
//            
//            
//
//        }
//
//  
//
//     /**
//      *  Factory class that keeps the parse method
//      */
//    public static class Factory{
//
//        
//        
//
//        /**
//        * static method to create the object
//        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
//        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
//        * Postcondition: If this object is an element, the reader is positioned at its end element
//        *                If this object is a complex type, the reader is positioned at the end element of its outer element
//        */
//        public static Fault parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
//            Fault object =
//                new Fault();
//
//            int event;
//            java.lang.String nillableValue = null;
//            java.lang.String prefix ="";
//            java.lang.String namespaceuri ="";
//            try {
//                
//                while (!reader.isStartElement() && !reader.isEndElement())
//                    reader.next();
//
//                
//                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
//                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
//                        "type");
//                  if (fullTypeName!=null){
//                    java.lang.String nsPrefix = null;
//                    if (fullTypeName.indexOf(":") > -1){
//                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
//                    }
//                    nsPrefix = nsPrefix==null?"":nsPrefix;
//
//                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
//                    
//                            if (!"Fault".equals(type)){
//                                //find namespace for the prefix
//                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
//                                return (Fault)ExtensionMapper.getTypeObject(
//                                     nsUri,type,reader);
//                              }
//                        
//
//                  }
//                
//
//                }
//
//                
//
//                
//                // Note all attributes that were handled. Used to differ normal attributes
//                // from anyAttributes.
//                java.util.Vector handledAttributes = new java.util.Vector();
//                
//
//                
//                    
//                    reader.next();
//                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","FaultCode").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"FaultCode" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setFaultCode(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","FaultString").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"FaultString" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setFaultString(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Detail").equals(reader.getName())){
//                                
//                                                object.setDetail(Detail.Factory.parse(reader));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                  
//                            while (!reader.isStartElement() && !reader.isEndElement())
//                                reader.next();
//                            
//                                if (reader.isStartElement())
//                                // A start element we are not expecting indicates a trailing invalid property
//                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                            
//
//
//
//            } catch (javax.xml.stream.XMLStreamException e) {
//                throw new java.lang.Exception(e);
//            }
//
//            return object;
//        }
//
//        }//end of factory class
//
//        
//
//        }
//           
//    
//        public static class Detail
//        implements org.apache.axis2.databinding.ADBBean{
//        /* This type was generated from the piece of schema that had
//                name = Detail
//                Namespace URI = http://www.adtec.com.cn
//                Namespace Prefix = ns1
//                */
//            
//
//                        /**
//                        * field for TxnStat
//                        */
//
//                        
//                                    protected java.lang.String localTxnStat ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTxnStatTracker = false ;
//
//                           public boolean isTxnStatSpecified(){
//                               return localTxnStatTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTxnStat(){
//                               return localTxnStat;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TxnStat
//                               */
//                               public void setTxnStat(java.lang.String param){
//                            localTxnStatTracker = param != null;
//                                   
//                                            this.localTxnStat=param;
//                                    
//
//                               }
//                            
//
//     
//     
//        /**
//        *
//        * @param parentQName
//        * @param factory
//        * @return org.apache.axiom.om.OMElement
//        */
//       public org.apache.axiom.om.OMElement getOMElement (
//               final javax.xml.namespace.QName parentQName,
//               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{
//
//
//        
//               org.apache.axiom.om.OMDataSource dataSource =
//                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
//               return factory.createOMElement(dataSource,parentQName);
//            
//        }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter)
//                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//                           serialize(parentQName,xmlWriter,false);
//         }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                               javax.xml.stream.XMLStreamWriter xmlWriter,
//                               boolean serializeType)
//            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//            
//                
//
//
//                java.lang.String prefix = null;
//                java.lang.String namespace = null;
//                
//
//                    prefix = parentQName.getPrefix();
//                    namespace = parentQName.getNamespaceURI();
//                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
//                
//                  if (serializeType){
//               
//
//                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adtec.com.cn");
//                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           namespacePrefix+":Detail",
//                           xmlWriter);
//                   } else {
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           "Detail",
//                           xmlWriter);
//                   }
//
//               
//                   }
//                if (localTxnStatTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TxnStat", xmlWriter);
//                             
//
//                                          if (localTxnStat==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TxnStat cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTxnStat);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             }
//                    xmlWriter.writeEndElement();
//               
//
//        }
//
//        private static java.lang.String generatePrefix(java.lang.String namespace) {
//            if(namespace.equals("http://www.adtec.com.cn")){
//                return "ns1";
//            }
//            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//        }
//
//        /**
//         * Utility method to write an element start tag.
//         */
//        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
//            if (writerPrefix != null) {
//                xmlWriter.writeStartElement(namespace, localPart);
//            } else {
//                if (namespace.length() == 0) {
//                    prefix = "";
//                } else if (prefix == null) {
//                    prefix = generatePrefix(namespace);
//                }
//
//                xmlWriter.writeStartElement(prefix, localPart, namespace);
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//        }
//        
//        /**
//         * Util method to write an attribute with the ns prefix
//         */
//        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (xmlWriter.getPrefix(namespace) == null) {
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            xmlWriter.writeAttribute(namespace,attName,attValue);
//        }
//
//        /**
//         * Util method to write an attribute without the ns prefix
//         */
//        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (namespace.equals("")) {
//                xmlWriter.writeAttribute(attName,attValue);
//            } else {
//                registerPrefix(xmlWriter, namespace);
//                xmlWriter.writeAttribute(namespace,attName,attValue);
//            }
//        }
//
//
//           /**
//             * Util method to write an attribute without the ns prefix
//             */
//            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
//                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//                java.lang.String attributeNamespace = qname.getNamespaceURI();
//                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
//                if (attributePrefix == null) {
//                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
//                }
//                java.lang.String attributeValue;
//                if (attributePrefix.trim().length() > 0) {
//                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
//                } else {
//                    attributeValue = qname.getLocalPart();
//                }
//
//                if (namespace.equals("")) {
//                    xmlWriter.writeAttribute(attName, attributeValue);
//                } else {
//                    registerPrefix(xmlWriter, namespace);
//                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
//                }
//            }
//        /**
//         *  method to handle Qnames
//         */
//
//        private void writeQName(javax.xml.namespace.QName qname,
//                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String namespaceURI = qname.getNamespaceURI();
//            if (namespaceURI != null) {
//                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
//                if (prefix == null) {
//                    prefix = generatePrefix(namespaceURI);
//                    xmlWriter.writeNamespace(prefix, namespaceURI);
//                    xmlWriter.setPrefix(prefix,namespaceURI);
//                }
//
//                if (prefix.trim().length() > 0){
//                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                } else {
//                    // i.e this is the default namespace
//                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                }
//
//            } else {
//                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//            }
//        }
//
//        private void writeQNames(javax.xml.namespace.QName[] qnames,
//                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//            if (qnames != null) {
//                // we have to store this data until last moment since it is not possible to write any
//                // namespace data after writing the charactor data
//                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
//                java.lang.String namespaceURI = null;
//                java.lang.String prefix = null;
//
//                for (int i = 0; i < qnames.length; i++) {
//                    if (i > 0) {
//                        stringToWrite.append(" ");
//                    }
//                    namespaceURI = qnames[i].getNamespaceURI();
//                    if (namespaceURI != null) {
//                        prefix = xmlWriter.getPrefix(namespaceURI);
//                        if ((prefix == null) || (prefix.length() == 0)) {
//                            prefix = generatePrefix(namespaceURI);
//                            xmlWriter.writeNamespace(prefix, namespaceURI);
//                            xmlWriter.setPrefix(prefix,namespaceURI);
//                        }
//
//                        if (prefix.trim().length() > 0){
//                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        } else {
//                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        }
//                    } else {
//                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                    }
//                }
//                xmlWriter.writeCharacters(stringToWrite.toString());
//            }
//
//        }
//
//
//        /**
//         * Register a namespace prefix
//         */
//        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
//            java.lang.String prefix = xmlWriter.getPrefix(namespace);
//            if (prefix == null) {
//                prefix = generatePrefix(namespace);
//                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
//                while (true) {
//                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
//                    if (uri == null || uri.length() == 0) {
//                        break;
//                    }
//                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//                }
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            return prefix;
//        }
//
//
//  
//        /**
//        * databinding method to get an XML representation of this object
//        *
//        */
//        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
//                    throws org.apache.axis2.databinding.ADBException{
//
//
//        
//                 java.util.ArrayList elementList = new java.util.ArrayList();
//                 java.util.ArrayList attribList = new java.util.ArrayList();
//
//                 if (localTxnStatTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TxnStat"));
//                                 
//                                        if (localTxnStat != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTxnStat));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TxnStat cannot be null!!");
//                                        }
//                                    }
//
//                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
//            
//            
//
//        }
//
//  
//
//     /**
//      *  Factory class that keeps the parse method
//      */
//    public static class Factory{
//
//        
//        
//
//        /**
//        * static method to create the object
//        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
//        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
//        * Postcondition: If this object is an element, the reader is positioned at its end element
//        *                If this object is a complex type, the reader is positioned at the end element of its outer element
//        */
//        public static Detail parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
//            Detail object =
//                new Detail();
//
//            int event;
//            java.lang.String nillableValue = null;
//            java.lang.String prefix ="";
//            java.lang.String namespaceuri ="";
//            try {
//                
//                while (!reader.isStartElement() && !reader.isEndElement())
//                    reader.next();
//
//                
//                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
//                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
//                        "type");
//                  if (fullTypeName!=null){
//                    java.lang.String nsPrefix = null;
//                    if (fullTypeName.indexOf(":") > -1){
//                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
//                    }
//                    nsPrefix = nsPrefix==null?"":nsPrefix;
//
//                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
//                    
//                            if (!"Detail".equals(type)){
//                                //find namespace for the prefix
//                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
//                                return (Detail)ExtensionMapper.getTypeObject(
//                                     nsUri,type,reader);
//                              }
//                        
//
//                  }
//                
//
//                }
//
//                
//
//                
//                // Note all attributes that were handled. Used to differ normal attributes
//                // from anyAttributes.
//                java.util.Vector handledAttributes = new java.util.Vector();
//                
//
//                
//                    
//                    reader.next();
//                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TxnStat").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TxnStat" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTxnStat(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                  
//                            while (!reader.isStartElement() && !reader.isEndElement())
//                                reader.next();
//                            
//                                if (reader.isStartElement())
//                                // A start element we are not expecting indicates a trailing invalid property
//                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                            
//
//
//
//            } catch (javax.xml.stream.XMLStreamException e) {
//                throw new java.lang.Exception(e);
//            }
//
//            return object;
//        }
//
//        }//end of factory class
//
//        
//
//        }
//           
//    
//        public static class ResponseHeader
//        implements org.apache.axis2.databinding.ADBBean{
//        /* This type was generated from the piece of schema that had
//                name = ResponseHeader
//                Namespace URI = http://www.adtec.com.cn
//                Namespace Prefix = ns1
//                */
//            
//
//                        /**
//                        * field for VerNo
//                        */
//
//                        
//                                    protected java.lang.String localVerNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localVerNoTracker = false ;
//
//                           public boolean isVerNoSpecified(){
//                               return localVerNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getVerNo(){
//                               return localVerNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param VerNo
//                               */
//                               public void setVerNo(java.lang.String param){
//                            localVerNoTracker = param != null;
//                                   
//                                            this.localVerNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RespSysCd
//                        */
//
//                        
//                                    protected java.lang.String localRespSysCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRespSysCdTracker = false ;
//
//                           public boolean isRespSysCdSpecified(){
//                               return localRespSysCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRespSysCd(){
//                               return localRespSysCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RespSysCd
//                               */
//                               public void setRespSysCd(java.lang.String param){
//                            localRespSysCdTracker = param != null;
//                                   
//                                            this.localRespSysCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RespSecCd
//                        */
//
//                        
//                                    protected java.lang.String localRespSecCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRespSecCdTracker = false ;
//
//                           public boolean isRespSecCdSpecified(){
//                               return localRespSecCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRespSecCd(){
//                               return localRespSecCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RespSecCd
//                               */
//                               public void setRespSecCd(java.lang.String param){
//                            localRespSecCdTracker = param != null;
//                                   
//                                            this.localRespSecCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TxnCd
//                        */
//
//                        
//                                    protected java.lang.String localTxnCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTxnCdTracker = false ;
//
//                           public boolean isTxnCdSpecified(){
//                               return localTxnCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTxnCd(){
//                               return localTxnCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TxnCd
//                               */
//                               public void setTxnCd(java.lang.String param){
//                            localTxnCdTracker = param != null;
//                                   
//                                            this.localTxnCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ReqDt
//                        */
//
//                        
//                                    protected java.lang.String localReqDt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localReqDtTracker = false ;
//
//                           public boolean isReqDtSpecified(){
//                               return localReqDtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getReqDt(){
//                               return localReqDt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ReqDt
//                               */
//                               public void setReqDt(java.lang.String param){
//                            localReqDtTracker = param != null;
//                                   
//                                            this.localReqDt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ReqTm
//                        */
//
//                        
//                                    protected java.lang.String localReqTm ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localReqTmTracker = false ;
//
//                           public boolean isReqTmSpecified(){
//                               return localReqTmTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getReqTm(){
//                               return localReqTm;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ReqTm
//                               */
//                               public void setReqTm(java.lang.String param){
//                            localReqTmTracker = param != null;
//                                   
//                                            this.localReqTm=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ReqSeqNo
//                        */
//
//                        
//                                    protected java.lang.String localReqSeqNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localReqSeqNoTracker = false ;
//
//                           public boolean isReqSeqNoSpecified(){
//                               return localReqSeqNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getReqSeqNo(){
//                               return localReqSeqNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ReqSeqNo
//                               */
//                               public void setReqSeqNo(java.lang.String param){
//                            localReqSeqNoTracker = param != null;
//                                   
//                                            this.localReqSeqNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for SvrDt
//                        */
//
//                        
//                                    protected java.lang.String localSvrDt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localSvrDtTracker = false ;
//
//                           public boolean isSvrDtSpecified(){
//                               return localSvrDtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getSvrDt(){
//                               return localSvrDt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param SvrDt
//                               */
//                               public void setSvrDt(java.lang.String param){
//                            localSvrDtTracker = param != null;
//                                   
//                                            this.localSvrDt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for SvrTm
//                        */
//
//                        
//                                    protected java.lang.String localSvrTm ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localSvrTmTracker = false ;
//
//                           public boolean isSvrTmSpecified(){
//                               return localSvrTmTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getSvrTm(){
//                               return localSvrTm;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param SvrTm
//                               */
//                               public void setSvrTm(java.lang.String param){
//                            localSvrTmTracker = param != null;
//                                   
//                                            this.localSvrTm=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for SvrSeqNo
//                        */
//
//                        
//                                    protected java.lang.String localSvrSeqNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localSvrSeqNoTracker = false ;
//
//                           public boolean isSvrSeqNoSpecified(){
//                               return localSvrSeqNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getSvrSeqNo(){
//                               return localSvrSeqNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param SvrSeqNo
//                               */
//                               public void setSvrSeqNo(java.lang.String param){
//                            localSvrSeqNoTracker = param != null;
//                                   
//                                            this.localSvrSeqNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RcvFileNme
//                        */
//
//                        
//                                    protected java.lang.String localRcvFileNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRcvFileNmeTracker = false ;
//
//                           public boolean isRcvFileNmeSpecified(){
//                               return localRcvFileNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRcvFileNme(){
//                               return localRcvFileNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RcvFileNme
//                               */
//                               public void setRcvFileNme(java.lang.String param){
//                            localRcvFileNmeTracker = param != null;
//                                   
//                                            this.localRcvFileNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TotNum
//                        */
//
//                        
//                                    protected java.lang.String localTotNum ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTotNumTracker = false ;
//
//                           public boolean isTotNumSpecified(){
//                               return localTotNumTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTotNum(){
//                               return localTotNum;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TotNum
//                               */
//                               public void setTotNum(java.lang.String param){
//                            localTotNumTracker = param != null;
//                                   
//                                            this.localTotNum=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CurrRecNum
//                        */
//
//                        
//                                    protected java.lang.String localCurrRecNum ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCurrRecNumTracker = false ;
//
//                           public boolean isCurrRecNumSpecified(){
//                               return localCurrRecNumTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCurrRecNum(){
//                               return localCurrRecNum;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CurrRecNum
//                               */
//                               public void setCurrRecNum(java.lang.String param){
//                            localCurrRecNumTracker = param != null;
//                                   
//                                            this.localCurrRecNum=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for FileHMac
//                        */
//
//                        
//                                    protected java.lang.String localFileHMac ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localFileHMacTracker = false ;
//
//                           public boolean isFileHMacSpecified(){
//                               return localFileHMacTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getFileHMac(){
//                               return localFileHMac;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param FileHMac
//                               */
//                               public void setFileHMac(java.lang.String param){
//                            localFileHMacTracker = param != null;
//                                   
//                                            this.localFileHMac=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for HMac
//                        */
//
//                        
//                                    protected java.lang.String localHMac ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localHMacTracker = false ;
//
//                           public boolean isHMacSpecified(){
//                               return localHMacTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getHMac(){
//                               return localHMac;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param HMac
//                               */
//                               public void setHMac(java.lang.String param){
//                            localHMacTracker = param != null;
//                                   
//                                            this.localHMac=param;
//                                    
//
//                               }
//                            
//
//     
//     
//        /**
//        *
//        * @param parentQName
//        * @param factory
//        * @return org.apache.axiom.om.OMElement
//        */
//       public org.apache.axiom.om.OMElement getOMElement (
//               final javax.xml.namespace.QName parentQName,
//               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{
//
//
//        
//               org.apache.axiom.om.OMDataSource dataSource =
//                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
//               return factory.createOMElement(dataSource,parentQName);
//            
//        }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter)
//                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//                           serialize(parentQName,xmlWriter,false);
//         }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                               javax.xml.stream.XMLStreamWriter xmlWriter,
//                               boolean serializeType)
//            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//            
//                
//
//
//                java.lang.String prefix = null;
//                java.lang.String namespace = null;
//                
//
//                    prefix = parentQName.getPrefix();
//                    namespace = parentQName.getNamespaceURI();
//                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
//                
//                  if (serializeType){
//               
//
//                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adtec.com.cn");
//                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           namespacePrefix+":ResponseHeader",
//                           xmlWriter);
//                   } else {
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           "ResponseHeader",
//                           xmlWriter);
//                   }
//
//               
//                   }
//                if (localVerNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "VerNo", xmlWriter);
//                             
//
//                                          if (localVerNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("VerNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localVerNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRespSysCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RespSysCd", xmlWriter);
//                             
//
//                                          if (localRespSysCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RespSysCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRespSysCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRespSecCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RespSecCd", xmlWriter);
//                             
//
//                                          if (localRespSecCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RespSecCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRespSecCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTxnCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TxnCd", xmlWriter);
//                             
//
//                                          if (localTxnCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TxnCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTxnCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localReqDtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ReqDt", xmlWriter);
//                             
//
//                                          if (localReqDt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ReqDt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localReqDt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localReqTmTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ReqTm", xmlWriter);
//                             
//
//                                          if (localReqTm==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ReqTm cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localReqTm);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localReqSeqNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ReqSeqNo", xmlWriter);
//                             
//
//                                          if (localReqSeqNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ReqSeqNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localReqSeqNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localSvrDtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "SvrDt", xmlWriter);
//                             
//
//                                          if (localSvrDt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("SvrDt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localSvrDt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localSvrTmTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "SvrTm", xmlWriter);
//                             
//
//                                          if (localSvrTm==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("SvrTm cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localSvrTm);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localSvrSeqNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "SvrSeqNo", xmlWriter);
//                             
//
//                                          if (localSvrSeqNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("SvrSeqNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localSvrSeqNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRcvFileNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RcvFileNme", xmlWriter);
//                             
//
//                                          if (localRcvFileNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RcvFileNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRcvFileNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTotNumTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TotNum", xmlWriter);
//                             
//
//                                          if (localTotNum==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TotNum cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTotNum);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCurrRecNumTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CurrRecNum", xmlWriter);
//                             
//
//                                          if (localCurrRecNum==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CurrRecNum cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCurrRecNum);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localFileHMacTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "FileHMac", xmlWriter);
//                             
//
//                                          if (localFileHMac==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("FileHMac cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localFileHMac);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localHMacTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "HMac", xmlWriter);
//                             
//
//                                          if (localHMac==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("HMac cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localHMac);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             }
//                    xmlWriter.writeEndElement();
//               
//
//        }
//
//        private static java.lang.String generatePrefix(java.lang.String namespace) {
//            if(namespace.equals("http://www.adtec.com.cn")){
//                return "ns1";
//            }
//            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//        }
//
//        /**
//         * Utility method to write an element start tag.
//         */
//        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
//            if (writerPrefix != null) {
//                xmlWriter.writeStartElement(namespace, localPart);
//            } else {
//                if (namespace.length() == 0) {
//                    prefix = "";
//                } else if (prefix == null) {
//                    prefix = generatePrefix(namespace);
//                }
//
//                xmlWriter.writeStartElement(prefix, localPart, namespace);
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//        }
//        
//        /**
//         * Util method to write an attribute with the ns prefix
//         */
//        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (xmlWriter.getPrefix(namespace) == null) {
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            xmlWriter.writeAttribute(namespace,attName,attValue);
//        }
//
//        /**
//         * Util method to write an attribute without the ns prefix
//         */
//        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (namespace.equals("")) {
//                xmlWriter.writeAttribute(attName,attValue);
//            } else {
//                registerPrefix(xmlWriter, namespace);
//                xmlWriter.writeAttribute(namespace,attName,attValue);
//            }
//        }
//
//
//           /**
//             * Util method to write an attribute without the ns prefix
//             */
//            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
//                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//                java.lang.String attributeNamespace = qname.getNamespaceURI();
//                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
//                if (attributePrefix == null) {
//                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
//                }
//                java.lang.String attributeValue;
//                if (attributePrefix.trim().length() > 0) {
//                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
//                } else {
//                    attributeValue = qname.getLocalPart();
//                }
//
//                if (namespace.equals("")) {
//                    xmlWriter.writeAttribute(attName, attributeValue);
//                } else {
//                    registerPrefix(xmlWriter, namespace);
//                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
//                }
//            }
//        /**
//         *  method to handle Qnames
//         */
//
//        private void writeQName(javax.xml.namespace.QName qname,
//                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String namespaceURI = qname.getNamespaceURI();
//            if (namespaceURI != null) {
//                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
//                if (prefix == null) {
//                    prefix = generatePrefix(namespaceURI);
//                    xmlWriter.writeNamespace(prefix, namespaceURI);
//                    xmlWriter.setPrefix(prefix,namespaceURI);
//                }
//
//                if (prefix.trim().length() > 0){
//                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                } else {
//                    // i.e this is the default namespace
//                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                }
//
//            } else {
//                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//            }
//        }
//
//        private void writeQNames(javax.xml.namespace.QName[] qnames,
//                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//            if (qnames != null) {
//                // we have to store this data until last moment since it is not possible to write any
//                // namespace data after writing the charactor data
//                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
//                java.lang.String namespaceURI = null;
//                java.lang.String prefix = null;
//
//                for (int i = 0; i < qnames.length; i++) {
//                    if (i > 0) {
//                        stringToWrite.append(" ");
//                    }
//                    namespaceURI = qnames[i].getNamespaceURI();
//                    if (namespaceURI != null) {
//                        prefix = xmlWriter.getPrefix(namespaceURI);
//                        if ((prefix == null) || (prefix.length() == 0)) {
//                            prefix = generatePrefix(namespaceURI);
//                            xmlWriter.writeNamespace(prefix, namespaceURI);
//                            xmlWriter.setPrefix(prefix,namespaceURI);
//                        }
//
//                        if (prefix.trim().length() > 0){
//                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        } else {
//                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        }
//                    } else {
//                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                    }
//                }
//                xmlWriter.writeCharacters(stringToWrite.toString());
//            }
//
//        }
//
//
//        /**
//         * Register a namespace prefix
//         */
//        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
//            java.lang.String prefix = xmlWriter.getPrefix(namespace);
//            if (prefix == null) {
//                prefix = generatePrefix(namespace);
//                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
//                while (true) {
//                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
//                    if (uri == null || uri.length() == 0) {
//                        break;
//                    }
//                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//                }
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            return prefix;
//        }
//
//
//  
//        /**
//        * databinding method to get an XML representation of this object
//        *
//        */
//        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
//                    throws org.apache.axis2.databinding.ADBException{
//
//
//        
//                 java.util.ArrayList elementList = new java.util.ArrayList();
//                 java.util.ArrayList attribList = new java.util.ArrayList();
//
//                 if (localVerNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "VerNo"));
//                                 
//                                        if (localVerNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVerNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("VerNo cannot be null!!");
//                                        }
//                                    } if (localRespSysCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RespSysCd"));
//                                 
//                                        if (localRespSysCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRespSysCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RespSysCd cannot be null!!");
//                                        }
//                                    } if (localRespSecCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RespSecCd"));
//                                 
//                                        if (localRespSecCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRespSecCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RespSecCd cannot be null!!");
//                                        }
//                                    } if (localTxnCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TxnCd"));
//                                 
//                                        if (localTxnCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTxnCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TxnCd cannot be null!!");
//                                        }
//                                    } if (localReqDtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ReqDt"));
//                                 
//                                        if (localReqDt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqDt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ReqDt cannot be null!!");
//                                        }
//                                    } if (localReqTmTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ReqTm"));
//                                 
//                                        if (localReqTm != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqTm));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ReqTm cannot be null!!");
//                                        }
//                                    } if (localReqSeqNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ReqSeqNo"));
//                                 
//                                        if (localReqSeqNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqSeqNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ReqSeqNo cannot be null!!");
//                                        }
//                                    } if (localSvrDtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "SvrDt"));
//                                 
//                                        if (localSvrDt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSvrDt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("SvrDt cannot be null!!");
//                                        }
//                                    } if (localSvrTmTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "SvrTm"));
//                                 
//                                        if (localSvrTm != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSvrTm));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("SvrTm cannot be null!!");
//                                        }
//                                    } if (localSvrSeqNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "SvrSeqNo"));
//                                 
//                                        if (localSvrSeqNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSvrSeqNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("SvrSeqNo cannot be null!!");
//                                        }
//                                    } if (localRcvFileNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RcvFileNme"));
//                                 
//                                        if (localRcvFileNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRcvFileNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RcvFileNme cannot be null!!");
//                                        }
//                                    } if (localTotNumTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TotNum"));
//                                 
//                                        if (localTotNum != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotNum));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TotNum cannot be null!!");
//                                        }
//                                    } if (localCurrRecNumTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CurrRecNum"));
//                                 
//                                        if (localCurrRecNum != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrRecNum));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CurrRecNum cannot be null!!");
//                                        }
//                                    } if (localFileHMacTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "FileHMac"));
//                                 
//                                        if (localFileHMac != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFileHMac));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("FileHMac cannot be null!!");
//                                        }
//                                    } if (localHMacTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "HMac"));
//                                 
//                                        if (localHMac != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHMac));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("HMac cannot be null!!");
//                                        }
//                                    }
//
//                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
//            
//            
//
//        }
//
//  
//
//     /**
//      *  Factory class that keeps the parse method
//      */
//    public static class Factory{
//
//        
//        
//
//        /**
//        * static method to create the object
//        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
//        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
//        * Postcondition: If this object is an element, the reader is positioned at its end element
//        *                If this object is a complex type, the reader is positioned at the end element of its outer element
//        */
//        public static ResponseHeader parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
//            ResponseHeader object =
//                new ResponseHeader();
//
//            int event;
//            java.lang.String nillableValue = null;
//            java.lang.String prefix ="";
//            java.lang.String namespaceuri ="";
//            try {
//                
//                while (!reader.isStartElement() && !reader.isEndElement())
//                    reader.next();
//
//                
//                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
//                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
//                        "type");
//                  if (fullTypeName!=null){
//                    java.lang.String nsPrefix = null;
//                    if (fullTypeName.indexOf(":") > -1){
//                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
//                    }
//                    nsPrefix = nsPrefix==null?"":nsPrefix;
//
//                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
//                    
//                            if (!"ResponseHeader".equals(type)){
//                                //find namespace for the prefix
//                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
//                                return (ResponseHeader)ExtensionMapper.getTypeObject(
//                                     nsUri,type,reader);
//                              }
//                        
//
//                  }
//                
//
//                }
//
//                
//
//                
//                // Note all attributes that were handled. Used to differ normal attributes
//                // from anyAttributes.
//                java.util.Vector handledAttributes = new java.util.Vector();
//                
//
//                
//                    
//                    reader.next();
//                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","VerNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"VerNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setVerNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RespSysCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RespSysCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRespSysCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RespSecCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RespSecCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRespSecCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TxnCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TxnCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTxnCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ReqDt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ReqDt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setReqDt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ReqTm").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ReqTm" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setReqTm(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ReqSeqNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ReqSeqNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setReqSeqNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","SvrDt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"SvrDt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setSvrDt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","SvrTm").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"SvrTm" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setSvrTm(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","SvrSeqNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"SvrSeqNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setSvrSeqNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RcvFileNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RcvFileNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRcvFileNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TotNum").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TotNum" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTotNum(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CurrRecNum").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CurrRecNum" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCurrRecNum(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","FileHMac").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"FileHMac" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setFileHMac(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","HMac").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"HMac" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setHMac(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                  
//                            while (!reader.isStartElement() && !reader.isEndElement())
//                                reader.next();
//                            
//                                if (reader.isStartElement())
//                                // A start element we are not expecting indicates a trailing invalid property
//                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                            
//
//
//
//            } catch (javax.xml.stream.XMLStreamException e) {
//                throw new java.lang.Exception(e);
//            }
//
//            return object;
//        }
//
//        }//end of factory class
//
//        
//
//        }
//           
//    
//        public static class ResponseBody
//        implements org.apache.axis2.databinding.ADBBean{
//        /* This type was generated from the piece of schema that had
//                name = ResponseBody
//                Namespace URI = http://www.adtec.com.cn
//                Namespace Prefix = ns1
//                */
//            
//
//     
//     
//        /**
//        *
//        * @param parentQName
//        * @param factory
//        * @return org.apache.axiom.om.OMElement
//        */
//       public org.apache.axiom.om.OMElement getOMElement (
//               final javax.xml.namespace.QName parentQName,
//               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{
//
//
//        
//               org.apache.axiom.om.OMDataSource dataSource =
//                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
//               return factory.createOMElement(dataSource,parentQName);
//            
//        }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter)
//                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//                           serialize(parentQName,xmlWriter,false);
//         }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                               javax.xml.stream.XMLStreamWriter xmlWriter,
//                               boolean serializeType)
//            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//            
//                
//
//
//                java.lang.String prefix = null;
//                java.lang.String namespace = null;
//                
//
//                    prefix = parentQName.getPrefix();
//                    namespace = parentQName.getNamespaceURI();
//                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
//                
//                  if (serializeType){
//               
//
//                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adtec.com.cn");
//                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           namespacePrefix+":ResponseBody",
//                           xmlWriter);
//                   } else {
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           "ResponseBody",
//                           xmlWriter);
//                   }
//
//               
//                   }
//               
//                    xmlWriter.writeEndElement();
//               
//
//        }
//
//        private static java.lang.String generatePrefix(java.lang.String namespace) {
//            if(namespace.equals("http://www.adtec.com.cn")){
//                return "ns1";
//            }
//            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//        }
//
//        /**
//         * Utility method to write an element start tag.
//         */
//        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
//            if (writerPrefix != null) {
//                xmlWriter.writeStartElement(namespace, localPart);
//            } else {
//                if (namespace.length() == 0) {
//                    prefix = "";
//                } else if (prefix == null) {
//                    prefix = generatePrefix(namespace);
//                }
//
//                xmlWriter.writeStartElement(prefix, localPart, namespace);
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//        }
//        
//        /**
//         * Util method to write an attribute with the ns prefix
//         */
//        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (xmlWriter.getPrefix(namespace) == null) {
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            xmlWriter.writeAttribute(namespace,attName,attValue);
//        }
//
//        /**
//         * Util method to write an attribute without the ns prefix
//         */
//        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (namespace.equals("")) {
//                xmlWriter.writeAttribute(attName,attValue);
//            } else {
//                registerPrefix(xmlWriter, namespace);
//                xmlWriter.writeAttribute(namespace,attName,attValue);
//            }
//        }
//
//
//           /**
//             * Util method to write an attribute without the ns prefix
//             */
//            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
//                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//                java.lang.String attributeNamespace = qname.getNamespaceURI();
//                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
//                if (attributePrefix == null) {
//                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
//                }
//                java.lang.String attributeValue;
//                if (attributePrefix.trim().length() > 0) {
//                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
//                } else {
//                    attributeValue = qname.getLocalPart();
//                }
//
//                if (namespace.equals("")) {
//                    xmlWriter.writeAttribute(attName, attributeValue);
//                } else {
//                    registerPrefix(xmlWriter, namespace);
//                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
//                }
//            }
//        /**
//         *  method to handle Qnames
//         */
//
//        private void writeQName(javax.xml.namespace.QName qname,
//                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String namespaceURI = qname.getNamespaceURI();
//            if (namespaceURI != null) {
//                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
//                if (prefix == null) {
//                    prefix = generatePrefix(namespaceURI);
//                    xmlWriter.writeNamespace(prefix, namespaceURI);
//                    xmlWriter.setPrefix(prefix,namespaceURI);
//                }
//
//                if (prefix.trim().length() > 0){
//                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                } else {
//                    // i.e this is the default namespace
//                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                }
//
//            } else {
//                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//            }
//        }
//
//        private void writeQNames(javax.xml.namespace.QName[] qnames,
//                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//            if (qnames != null) {
//                // we have to store this data until last moment since it is not possible to write any
//                // namespace data after writing the charactor data
//                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
//                java.lang.String namespaceURI = null;
//                java.lang.String prefix = null;
//
//                for (int i = 0; i < qnames.length; i++) {
//                    if (i > 0) {
//                        stringToWrite.append(" ");
//                    }
//                    namespaceURI = qnames[i].getNamespaceURI();
//                    if (namespaceURI != null) {
//                        prefix = xmlWriter.getPrefix(namespaceURI);
//                        if ((prefix == null) || (prefix.length() == 0)) {
//                            prefix = generatePrefix(namespaceURI);
//                            xmlWriter.writeNamespace(prefix, namespaceURI);
//                            xmlWriter.setPrefix(prefix,namespaceURI);
//                        }
//
//                        if (prefix.trim().length() > 0){
//                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        } else {
//                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        }
//                    } else {
//                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                    }
//                }
//                xmlWriter.writeCharacters(stringToWrite.toString());
//            }
//
//        }
//
//
//        /**
//         * Register a namespace prefix
//         */
//        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
//            java.lang.String prefix = xmlWriter.getPrefix(namespace);
//            if (prefix == null) {
//                prefix = generatePrefix(namespace);
//                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
//                while (true) {
//                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
//                    if (uri == null || uri.length() == 0) {
//                        break;
//                    }
//                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//                }
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            return prefix;
//        }
//
//
//  
//        /**
//        * databinding method to get an XML representation of this object
//        *
//        */
//        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
//                    throws org.apache.axis2.databinding.ADBException{
//
//
//        
//                 java.util.ArrayList elementList = new java.util.ArrayList();
//                 java.util.ArrayList attribList = new java.util.ArrayList();
//
//                
//
//                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
//            
//            
//
//        }
//
//  
//
//     /**
//      *  Factory class that keeps the parse method
//      */
//    public static class Factory{
//
//        
//        
//
//        /**
//        * static method to create the object
//        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
//        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
//        * Postcondition: If this object is an element, the reader is positioned at its end element
//        *                If this object is a complex type, the reader is positioned at the end element of its outer element
//        */
//        public static ResponseBody parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
//            ResponseBody object =
//                new ResponseBody();
//
//            int event;
//            java.lang.String nillableValue = null;
//            java.lang.String prefix ="";
//            java.lang.String namespaceuri ="";
//            try {
//                
//                while (!reader.isStartElement() && !reader.isEndElement())
//                    reader.next();
//
//                
//                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
//                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
//                        "type");
//                  if (fullTypeName!=null){
//                    java.lang.String nsPrefix = null;
//                    if (fullTypeName.indexOf(":") > -1){
//                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
//                    }
//                    nsPrefix = nsPrefix==null?"":nsPrefix;
//
//                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
//                    
//                            if (!"ResponseBody".equals(type)){
//                                //find namespace for the prefix
//                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
//                                return (ResponseBody)ExtensionMapper.getTypeObject(
//                                     nsUri,type,reader);
//                              }
//                        
//
//                  }
//                
//
//                }
//
//                
//
//                
//                // Note all attributes that were handled. Used to differ normal attributes
//                // from anyAttributes.
//                java.util.Vector handledAttributes = new java.util.Vector();
//                
//
//                
//                    
//                    reader.next();
//                  
//                            while (!reader.isStartElement() && !reader.isEndElement())
//                                reader.next();
//                            
//                                if (reader.isStartElement())
//                                // A start element we are not expecting indicates a trailing invalid property
//                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                            
//
//
//
//            } catch (javax.xml.stream.XMLStreamException e) {
//                throw new java.lang.Exception(e);
//            }
//
//            return object;
//        }
//
//        }//end of factory class
//
//        
//
//        }
//           
//    
//        public static class RequestHeader
//        implements org.apache.axis2.databinding.ADBBean{
//        /* This type was generated from the piece of schema that had
//                name = RequestHeader
//                Namespace URI = http://www.adtec.com.cn
//                Namespace Prefix = ns1
//                */
//            
//
//                        /**
//                        * field for VerNo
//                        */
//
//                        
//                                    protected java.lang.String localVerNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localVerNoTracker = false ;
//
//                           public boolean isVerNoSpecified(){
//                               return localVerNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getVerNo(){
//                               return localVerNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param VerNo
//                               */
//                               public void setVerNo(java.lang.String param){
//                            localVerNoTracker = param != null;
//                                   
//                                            this.localVerNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ReqSysCd
//                        */
//
//                        
//                                    protected java.lang.String localReqSysCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localReqSysCdTracker = false ;
//
//                           public boolean isReqSysCdSpecified(){
//                               return localReqSysCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getReqSysCd(){
//                               return localReqSysCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ReqSysCd
//                               */
//                               public void setReqSysCd(java.lang.String param){
//                            localReqSysCdTracker = param != null;
//                                   
//                                            this.localReqSysCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ReqSecCd
//                        */
//
//                        
//                                    protected java.lang.String localReqSecCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localReqSecCdTracker = false ;
//
//                           public boolean isReqSecCdSpecified(){
//                               return localReqSecCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getReqSecCd(){
//                               return localReqSecCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ReqSecCd
//                               */
//                               public void setReqSecCd(java.lang.String param){
//                            localReqSecCdTracker = param != null;
//                                   
//                                            this.localReqSecCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TxnTyp
//                        */
//
//                        
//                                    protected java.lang.String localTxnTyp ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTxnTypTracker = false ;
//
//                           public boolean isTxnTypSpecified(){
//                               return localTxnTypTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTxnTyp(){
//                               return localTxnTyp;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TxnTyp
//                               */
//                               public void setTxnTyp(java.lang.String param){
//                            localTxnTypTracker = param != null;
//                                   
//                                            this.localTxnTyp=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TxnMod
//                        */
//
//                        
//                                    protected java.lang.String localTxnMod ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTxnModTracker = false ;
//
//                           public boolean isTxnModSpecified(){
//                               return localTxnModTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTxnMod(){
//                               return localTxnMod;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TxnMod
//                               */
//                               public void setTxnMod(java.lang.String param){
//                            localTxnModTracker = param != null;
//                                   
//                                            this.localTxnMod=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TxnCd
//                        */
//
//                        
//                                    protected java.lang.String localTxnCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTxnCdTracker = false ;
//
//                           public boolean isTxnCdSpecified(){
//                               return localTxnCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTxnCd(){
//                               return localTxnCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TxnCd
//                               */
//                               public void setTxnCd(java.lang.String param){
//                            localTxnCdTracker = param != null;
//                                   
//                                            this.localTxnCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ReqDt
//                        */
//
//                        
//                                    protected java.lang.String localReqDt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localReqDtTracker = false ;
//
//                           public boolean isReqDtSpecified(){
//                               return localReqDtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getReqDt(){
//                               return localReqDt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ReqDt
//                               */
//                               public void setReqDt(java.lang.String param){
//                            localReqDtTracker = param != null;
//                                   
//                                            this.localReqDt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ReqTm
//                        */
//
//                        
//                                    protected java.lang.String localReqTm ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localReqTmTracker = false ;
//
//                           public boolean isReqTmSpecified(){
//                               return localReqTmTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getReqTm(){
//                               return localReqTm;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ReqTm
//                               */
//                               public void setReqTm(java.lang.String param){
//                            localReqTmTracker = param != null;
//                                   
//                                            this.localReqTm=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ReqSeqNo
//                        */
//
//                        
//                                    protected java.lang.String localReqSeqNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localReqSeqNoTracker = false ;
//
//                           public boolean isReqSeqNoSpecified(){
//                               return localReqSeqNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getReqSeqNo(){
//                               return localReqSeqNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ReqSeqNo
//                               */
//                               public void setReqSeqNo(java.lang.String param){
//                            localReqSeqNoTracker = param != null;
//                                   
//                                            this.localReqSeqNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ChnlNo
//                        */
//
//                        
//                                    protected java.lang.String localChnlNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localChnlNoTracker = false ;
//
//                           public boolean isChnlNoSpecified(){
//                               return localChnlNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getChnlNo(){
//                               return localChnlNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ChnlNo
//                               */
//                               public void setChnlNo(java.lang.String param){
//                            localChnlNoTracker = param != null;
//                                   
//                                            this.localChnlNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for BrchNo
//                        */
//
//                        
//                                    protected java.lang.String localBrchNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localBrchNoTracker = false ;
//
//                           public boolean isBrchNoSpecified(){
//                               return localBrchNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getBrchNo(){
//                               return localBrchNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param BrchNo
//                               */
//                               public void setBrchNo(java.lang.String param){
//                            localBrchNoTracker = param != null;
//                                   
//                                            this.localBrchNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TrmNo
//                        */
//
//                        
//                                    protected java.lang.String localTrmNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTrmNoTracker = false ;
//
//                           public boolean isTrmNoSpecified(){
//                               return localTrmNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTrmNo(){
//                               return localTrmNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TrmNo
//                               */
//                               public void setTrmNo(java.lang.String param){
//                            localTrmNoTracker = param != null;
//                                   
//                                            this.localTrmNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TlrNo
//                        */
//
//                        
//                                    protected java.lang.String localTlrNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTlrNoTracker = false ;
//
//                           public boolean isTlrNoSpecified(){
//                               return localTlrNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTlrNo(){
//                               return localTlrNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TlrNo
//                               */
//                               public void setTlrNo(java.lang.String param){
//                            localTlrNoTracker = param != null;
//                                   
//                                            this.localTlrNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for SndFileNme
//                        */
//
//                        
//                                    protected java.lang.String localSndFileNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localSndFileNmeTracker = false ;
//
//                           public boolean isSndFileNmeSpecified(){
//                               return localSndFileNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getSndFileNme(){
//                               return localSndFileNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param SndFileNme
//                               */
//                               public void setSndFileNme(java.lang.String param){
//                            localSndFileNmeTracker = param != null;
//                                   
//                                            this.localSndFileNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for BgnRec
//                        */
//
//                        
//                                    protected java.lang.String localBgnRec ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localBgnRecTracker = false ;
//
//                           public boolean isBgnRecSpecified(){
//                               return localBgnRecTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getBgnRec(){
//                               return localBgnRec;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param BgnRec
//                               */
//                               public void setBgnRec(java.lang.String param){
//                            localBgnRecTracker = param != null;
//                                   
//                                            this.localBgnRec=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for MaxRec
//                        */
//
//                        
//                                    protected java.lang.String localMaxRec ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localMaxRecTracker = false ;
//
//                           public boolean isMaxRecSpecified(){
//                               return localMaxRecTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getMaxRec(){
//                               return localMaxRec;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param MaxRec
//                               */
//                               public void setMaxRec(java.lang.String param){
//                            localMaxRecTracker = param != null;
//                                   
//                                            this.localMaxRec=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for FileHMac
//                        */
//
//                        
//                                    protected java.lang.String localFileHMac ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localFileHMacTracker = false ;
//
//                           public boolean isFileHMacSpecified(){
//                               return localFileHMacTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getFileHMac(){
//                               return localFileHMac;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param FileHMac
//                               */
//                               public void setFileHMac(java.lang.String param){
//                            localFileHMacTracker = param != null;
//                                   
//                                            this.localFileHMac=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for HMac
//                        */
//
//                        
//                                    protected java.lang.String localHMac ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localHMacTracker = false ;
//
//                           public boolean isHMacSpecified(){
//                               return localHMacTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getHMac(){
//                               return localHMac;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param HMac
//                               */
//                               public void setHMac(java.lang.String param){
//                            localHMacTracker = param != null;
//                                   
//                                            this.localHMac=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for AllChnSeq
//                        */
//
//                        
//                                    protected java.lang.String localAllChnSeq ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAllChnSeqTracker = false ;
//
//                           public boolean isAllChnSeqSpecified(){
//                               return localAllChnSeqTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAllChnSeq(){
//                               return localAllChnSeq;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param AllChnSeq
//                               */
//                               public void setAllChnSeq(java.lang.String param){
//                            localAllChnSeqTracker = param != null;
//                                   
//                                            this.localAllChnSeq=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TmOut
//                        */
//
//                        
//                                    protected java.lang.String localTmOut ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTmOutTracker = false ;
//
//                           public boolean isTmOutSpecified(){
//                               return localTmOutTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTmOut(){
//                               return localTmOut;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TmOut
//                               */
//                               public void setTmOut(java.lang.String param){
//                            localTmOutTracker = param != null;
//                                   
//                                            this.localTmOut=param;
//                                    
//
//                               }
//                            
//
//     
//     
//        /**
//        *
//        * @param parentQName
//        * @param factory
//        * @return org.apache.axiom.om.OMElement
//        */
//       public org.apache.axiom.om.OMElement getOMElement (
//               final javax.xml.namespace.QName parentQName,
//               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{
//
//
//        
//               org.apache.axiom.om.OMDataSource dataSource =
//                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
//               return factory.createOMElement(dataSource,parentQName);
//            
//        }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter)
//                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//                           serialize(parentQName,xmlWriter,false);
//         }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                               javax.xml.stream.XMLStreamWriter xmlWriter,
//                               boolean serializeType)
//            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//            
//                
//
//
//                java.lang.String prefix = null;
//                java.lang.String namespace = null;
//                
//
//                    prefix = parentQName.getPrefix();
//                    namespace = parentQName.getNamespaceURI();
//                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
//                
//                  if (serializeType){
//               
//
//                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adtec.com.cn");
//                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           namespacePrefix+":RequestHeader",
//                           xmlWriter);
//                   } else {
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           "RequestHeader",
//                           xmlWriter);
//                   }
//
//               
//                   }
//                if (localVerNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "VerNo", xmlWriter);
//                             
//
//                                          if (localVerNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("VerNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localVerNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localReqSysCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ReqSysCd", xmlWriter);
//                             
//
//                                          if (localReqSysCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ReqSysCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localReqSysCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localReqSecCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ReqSecCd", xmlWriter);
//                             
//
//                                          if (localReqSecCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ReqSecCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localReqSecCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTxnTypTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TxnTyp", xmlWriter);
//                             
//
//                                          if (localTxnTyp==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TxnTyp cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTxnTyp);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTxnModTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TxnMod", xmlWriter);
//                             
//
//                                          if (localTxnMod==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TxnMod cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTxnMod);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTxnCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TxnCd", xmlWriter);
//                             
//
//                                          if (localTxnCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TxnCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTxnCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localReqDtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ReqDt", xmlWriter);
//                             
//
//                                          if (localReqDt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ReqDt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localReqDt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localReqTmTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ReqTm", xmlWriter);
//                             
//
//                                          if (localReqTm==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ReqTm cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localReqTm);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localReqSeqNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ReqSeqNo", xmlWriter);
//                             
//
//                                          if (localReqSeqNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ReqSeqNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localReqSeqNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localChnlNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ChnlNo", xmlWriter);
//                             
//
//                                          if (localChnlNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ChnlNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localChnlNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localBrchNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "BrchNo", xmlWriter);
//                             
//
//                                          if (localBrchNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("BrchNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localBrchNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTrmNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TrmNo", xmlWriter);
//                             
//
//                                          if (localTrmNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TrmNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTrmNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTlrNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TlrNo", xmlWriter);
//                             
//
//                                          if (localTlrNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TlrNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTlrNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localSndFileNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "SndFileNme", xmlWriter);
//                             
//
//                                          if (localSndFileNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("SndFileNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localSndFileNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localBgnRecTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "BgnRec", xmlWriter);
//                             
//
//                                          if (localBgnRec==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("BgnRec cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localBgnRec);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localMaxRecTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "MaxRec", xmlWriter);
//                             
//
//                                          if (localMaxRec==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("MaxRec cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localMaxRec);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localFileHMacTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "FileHMac", xmlWriter);
//                             
//
//                                          if (localFileHMac==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("FileHMac cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localFileHMac);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localHMacTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "HMac", xmlWriter);
//                             
//
//                                          if (localHMac==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("HMac cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localHMac);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAllChnSeqTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "AllChnSeq", xmlWriter);
//                             
//
//                                          if (localAllChnSeq==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("AllChnSeq cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAllChnSeq);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTmOutTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TmOut", xmlWriter);
//                             
//
//                                          if (localTmOut==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TmOut cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTmOut);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             }
//                    xmlWriter.writeEndElement();
//               
//
//        }
//
//        private static java.lang.String generatePrefix(java.lang.String namespace) {
//            if(namespace.equals("http://www.adtec.com.cn")){
//                return "ns1";
//            }
//            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//        }
//
//        /**
//         * Utility method to write an element start tag.
//         */
//        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
//            if (writerPrefix != null) {
//                xmlWriter.writeStartElement(namespace, localPart);
//            } else {
//                if (namespace.length() == 0) {
//                    prefix = "";
//                } else if (prefix == null) {
//                    prefix = generatePrefix(namespace);
//                }
//
//                xmlWriter.writeStartElement(prefix, localPart, namespace);
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//        }
//        
//        /**
//         * Util method to write an attribute with the ns prefix
//         */
//        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (xmlWriter.getPrefix(namespace) == null) {
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            xmlWriter.writeAttribute(namespace,attName,attValue);
//        }
//
//        /**
//         * Util method to write an attribute without the ns prefix
//         */
//        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (namespace.equals("")) {
//                xmlWriter.writeAttribute(attName,attValue);
//            } else {
//                registerPrefix(xmlWriter, namespace);
//                xmlWriter.writeAttribute(namespace,attName,attValue);
//            }
//        }
//
//
//           /**
//             * Util method to write an attribute without the ns prefix
//             */
//            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
//                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//                java.lang.String attributeNamespace = qname.getNamespaceURI();
//                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
//                if (attributePrefix == null) {
//                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
//                }
//                java.lang.String attributeValue;
//                if (attributePrefix.trim().length() > 0) {
//                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
//                } else {
//                    attributeValue = qname.getLocalPart();
//                }
//
//                if (namespace.equals("")) {
//                    xmlWriter.writeAttribute(attName, attributeValue);
//                } else {
//                    registerPrefix(xmlWriter, namespace);
//                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
//                }
//            }
//        /**
//         *  method to handle Qnames
//         */
//
//        private void writeQName(javax.xml.namespace.QName qname,
//                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String namespaceURI = qname.getNamespaceURI();
//            if (namespaceURI != null) {
//                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
//                if (prefix == null) {
//                    prefix = generatePrefix(namespaceURI);
//                    xmlWriter.writeNamespace(prefix, namespaceURI);
//                    xmlWriter.setPrefix(prefix,namespaceURI);
//                }
//
//                if (prefix.trim().length() > 0){
//                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                } else {
//                    // i.e this is the default namespace
//                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                }
//
//            } else {
//                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//            }
//        }
//
//        private void writeQNames(javax.xml.namespace.QName[] qnames,
//                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//            if (qnames != null) {
//                // we have to store this data until last moment since it is not possible to write any
//                // namespace data after writing the charactor data
//                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
//                java.lang.String namespaceURI = null;
//                java.lang.String prefix = null;
//
//                for (int i = 0; i < qnames.length; i++) {
//                    if (i > 0) {
//                        stringToWrite.append(" ");
//                    }
//                    namespaceURI = qnames[i].getNamespaceURI();
//                    if (namespaceURI != null) {
//                        prefix = xmlWriter.getPrefix(namespaceURI);
//                        if ((prefix == null) || (prefix.length() == 0)) {
//                            prefix = generatePrefix(namespaceURI);
//                            xmlWriter.writeNamespace(prefix, namespaceURI);
//                            xmlWriter.setPrefix(prefix,namespaceURI);
//                        }
//
//                        if (prefix.trim().length() > 0){
//                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        } else {
//                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        }
//                    } else {
//                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                    }
//                }
//                xmlWriter.writeCharacters(stringToWrite.toString());
//            }
//
//        }
//
//
//        /**
//         * Register a namespace prefix
//         */
//        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
//            java.lang.String prefix = xmlWriter.getPrefix(namespace);
//            if (prefix == null) {
//                prefix = generatePrefix(namespace);
//                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
//                while (true) {
//                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
//                    if (uri == null || uri.length() == 0) {
//                        break;
//                    }
//                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//                }
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            return prefix;
//        }
//
//
//  
//        /**
//        * databinding method to get an XML representation of this object
//        *
//        */
//        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
//                    throws org.apache.axis2.databinding.ADBException{
//
//
//        
//                 java.util.ArrayList elementList = new java.util.ArrayList();
//                 java.util.ArrayList attribList = new java.util.ArrayList();
//
//                 if (localVerNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "VerNo"));
//                                 
//                                        if (localVerNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVerNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("VerNo cannot be null!!");
//                                        }
//                                    } if (localReqSysCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ReqSysCd"));
//                                 
//                                        if (localReqSysCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqSysCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ReqSysCd cannot be null!!");
//                                        }
//                                    } if (localReqSecCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ReqSecCd"));
//                                 
//                                        if (localReqSecCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqSecCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ReqSecCd cannot be null!!");
//                                        }
//                                    } if (localTxnTypTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TxnTyp"));
//                                 
//                                        if (localTxnTyp != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTxnTyp));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TxnTyp cannot be null!!");
//                                        }
//                                    } if (localTxnModTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TxnMod"));
//                                 
//                                        if (localTxnMod != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTxnMod));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TxnMod cannot be null!!");
//                                        }
//                                    } if (localTxnCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TxnCd"));
//                                 
//                                        if (localTxnCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTxnCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TxnCd cannot be null!!");
//                                        }
//                                    } if (localReqDtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ReqDt"));
//                                 
//                                        if (localReqDt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqDt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ReqDt cannot be null!!");
//                                        }
//                                    } if (localReqTmTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ReqTm"));
//                                 
//                                        if (localReqTm != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqTm));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ReqTm cannot be null!!");
//                                        }
//                                    } if (localReqSeqNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ReqSeqNo"));
//                                 
//                                        if (localReqSeqNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localReqSeqNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ReqSeqNo cannot be null!!");
//                                        }
//                                    } if (localChnlNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ChnlNo"));
//                                 
//                                        if (localChnlNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChnlNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ChnlNo cannot be null!!");
//                                        }
//                                    } if (localBrchNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "BrchNo"));
//                                 
//                                        if (localBrchNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBrchNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("BrchNo cannot be null!!");
//                                        }
//                                    } if (localTrmNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TrmNo"));
//                                 
//                                        if (localTrmNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTrmNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TrmNo cannot be null!!");
//                                        }
//                                    } if (localTlrNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TlrNo"));
//                                 
//                                        if (localTlrNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTlrNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TlrNo cannot be null!!");
//                                        }
//                                    } if (localSndFileNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "SndFileNme"));
//                                 
//                                        if (localSndFileNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSndFileNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("SndFileNme cannot be null!!");
//                                        }
//                                    } if (localBgnRecTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "BgnRec"));
//                                 
//                                        if (localBgnRec != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBgnRec));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("BgnRec cannot be null!!");
//                                        }
//                                    } if (localMaxRecTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "MaxRec"));
//                                 
//                                        if (localMaxRec != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMaxRec));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("MaxRec cannot be null!!");
//                                        }
//                                    } if (localFileHMacTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "FileHMac"));
//                                 
//                                        if (localFileHMac != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFileHMac));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("FileHMac cannot be null!!");
//                                        }
//                                    } if (localHMacTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "HMac"));
//                                 
//                                        if (localHMac != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localHMac));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("HMac cannot be null!!");
//                                        }
//                                    } if (localAllChnSeqTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "AllChnSeq"));
//                                 
//                                        if (localAllChnSeq != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAllChnSeq));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("AllChnSeq cannot be null!!");
//                                        }
//                                    } if (localTmOutTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TmOut"));
//                                 
//                                        if (localTmOut != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTmOut));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TmOut cannot be null!!");
//                                        }
//                                    }
//
//                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
//            
//            
//
//        }
//
//  
//
//     /**
//      *  Factory class that keeps the parse method
//      */
//    public static class Factory{
//
//        
//        
//
//        /**
//        * static method to create the object
//        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
//        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
//        * Postcondition: If this object is an element, the reader is positioned at its end element
//        *                If this object is a complex type, the reader is positioned at the end element of its outer element
//        */
//        public static RequestHeader parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
//            RequestHeader object =
//                new RequestHeader();
//
//            int event;
//            java.lang.String nillableValue = null;
//            java.lang.String prefix ="";
//            java.lang.String namespaceuri ="";
//            try {
//                
//                while (!reader.isStartElement() && !reader.isEndElement())
//                    reader.next();
//
//                
//                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
//                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
//                        "type");
//                  if (fullTypeName!=null){
//                    java.lang.String nsPrefix = null;
//                    if (fullTypeName.indexOf(":") > -1){
//                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
//                    }
//                    nsPrefix = nsPrefix==null?"":nsPrefix;
//
//                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
//                    
//                            if (!"RequestHeader".equals(type)){
//                                //find namespace for the prefix
//                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
//                                return (RequestHeader)ExtensionMapper.getTypeObject(
//                                     nsUri,type,reader);
//                              }
//                        
//
//                  }
//                
//
//                }
//
//                
//
//                
//                // Note all attributes that were handled. Used to differ normal attributes
//                // from anyAttributes.
//                java.util.Vector handledAttributes = new java.util.Vector();
//                
//
//                
//                    
//                    reader.next();
//                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","VerNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"VerNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setVerNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ReqSysCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ReqSysCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setReqSysCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ReqSecCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ReqSecCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setReqSecCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TxnTyp").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TxnTyp" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTxnTyp(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TxnMod").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TxnMod" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTxnMod(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TxnCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TxnCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTxnCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ReqDt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ReqDt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setReqDt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ReqTm").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ReqTm" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setReqTm(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ReqSeqNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ReqSeqNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setReqSeqNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ChnlNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ChnlNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setChnlNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","BrchNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"BrchNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setBrchNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TrmNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TrmNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTrmNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TlrNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TlrNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTlrNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","SndFileNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"SndFileNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setSndFileNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","BgnRec").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"BgnRec" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setBgnRec(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","MaxRec").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"MaxRec" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setMaxRec(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","FileHMac").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"FileHMac" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setFileHMac(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","HMac").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"HMac" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setHMac(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","AllChnSeq").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"AllChnSeq" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAllChnSeq(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TmOut").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TmOut" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTmOut(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                  
//                            while (!reader.isStartElement() && !reader.isEndElement())
//                                reader.next();
//                            
//                                if (reader.isStartElement())
//                                // A start element we are not expecting indicates a trailing invalid property
//                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                            
//
//
//
//            } catch (javax.xml.stream.XMLStreamException e) {
//                throw new java.lang.Exception(e);
//            }
//
//            return object;
//        }
//
//        }//end of factory class
//
//        
//
//        }
//           
//    
//        public static class RequestBody
//        implements org.apache.axis2.databinding.ADBBean{
//        /* This type was generated from the piece of schema that had
//                name = RequestBody
//                Namespace URI = http://www.adtec.com.cn
//                Namespace Prefix = ns1
//                */
//            
//
//                        /**
//                        * field for Channel
//                        */
//
//                        
//                                    protected java.lang.String localChannel ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localChannelTracker = false ;
//
//                           public boolean isChannelSpecified(){
//                               return localChannelTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getChannel(){
//                               return localChannel;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Channel
//                               */
//                               public void setChannel(java.lang.String param){
//                            localChannelTracker = param != null;
//                                   
//                                            this.localChannel=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for SysCd
//                        */
//
//                        
//                                    protected java.lang.String localSysCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localSysCdTracker = false ;
//
//                           public boolean isSysCdSpecified(){
//                               return localSysCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getSysCd(){
//                               return localSysCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param SysCd
//                               */
//                               public void setSysCd(java.lang.String param){
//                            localSysCdTracker = param != null;
//                                   
//                                            this.localSysCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TranType
//                        */
//
//                        
//                                    protected java.lang.String localTranType ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTranTypeTracker = false ;
//
//                           public boolean isTranTypeSpecified(){
//                               return localTranTypeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTranType(){
//                               return localTranType;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TranType
//                               */
//                               public void setTranType(java.lang.String param){
//                            localTranTypeTracker = param != null;
//                                   
//                                            this.localTranType=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for PrdCode
//                        */
//
//                        
//                                    protected java.lang.String localPrdCode ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localPrdCodeTracker = false ;
//
//                           public boolean isPrdCodeSpecified(){
//                               return localPrdCodeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getPrdCode(){
//                               return localPrdCode;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param PrdCode
//                               */
//                               public void setPrdCode(java.lang.String param){
//                            localPrdCodeTracker = param != null;
//                                   
//                                            this.localPrdCode=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Flag
//                        */
//
//                        
//                                    protected java.lang.String localFlag ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localFlagTracker = false ;
//
//                           public boolean isFlagSpecified(){
//                               return localFlagTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getFlag(){
//                               return localFlag;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Flag
//                               */
//                               public void setFlag(java.lang.String param){
//                            localFlagTracker = param != null;
//                                   
//                                            this.localFlag=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Attch
//                        */
//
//                        
//                                    protected java.lang.String localAttch ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAttchTracker = false ;
//
//                           public boolean isAttchSpecified(){
//                               return localAttchTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAttch(){
//                               return localAttch;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Attch
//                               */
//                               public void setAttch(java.lang.String param){
//                            localAttchTracker = param != null;
//                                   
//                                            this.localAttch=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Memo
//                        */
//
//                        
//                                    protected java.lang.String localMemo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localMemoTracker = false ;
//
//                           public boolean isMemoSpecified(){
//                               return localMemoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getMemo(){
//                               return localMemo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Memo
//                               */
//                               public void setMemo(java.lang.String param){
//                            localMemoTracker = param != null;
//                                   
//                                            this.localMemo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for DecAttch
//                        */
//
//                        
//                                    protected java.lang.String localDecAttch ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localDecAttchTracker = false ;
//
//                           public boolean isDecAttchSpecified(){
//                               return localDecAttchTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getDecAttch(){
//                               return localDecAttch;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param DecAttch
//                               */
//                               public void setDecAttch(java.lang.String param){
//                            localDecAttchTracker = param != null;
//                                   
//                                            this.localDecAttch=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Rmrk
//                        */
//
//                        
//                                    protected java.lang.String localRmrk ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRmrkTracker = false ;
//
//                           public boolean isRmrkSpecified(){
//                               return localRmrkTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRmrk(){
//                               return localRmrk;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Rmrk
//                               */
//                               public void setRmrk(java.lang.String param){
//                            localRmrkTracker = param != null;
//                                   
//                                            this.localRmrk=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for IssRmrk
//                        */
//
//                        
//                                    protected java.lang.String localIssRmrk ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localIssRmrkTracker = false ;
//
//                           public boolean isIssRmrkSpecified(){
//                               return localIssRmrkTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getIssRmrk(){
//                               return localIssRmrk;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param IssRmrk
//                               */
//                               public void setIssRmrk(java.lang.String param){
//                            localIssRmrkTracker = param != null;
//                                   
//                                            this.localIssRmrk=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for QryTyp
//                        */
//
//                        
//                                    protected java.lang.String localQryTyp ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localQryTypTracker = false ;
//
//                           public boolean isQryTypSpecified(){
//                               return localQryTypTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getQryTyp(){
//                               return localQryTyp;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param QryTyp
//                               */
//                               public void setQryTyp(java.lang.String param){
//                            localQryTypTracker = param != null;
//                                   
//                                            this.localQryTyp=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for PayMod
//                        */
//
//                        
//                                    protected java.lang.String localPayMod ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localPayModTracker = false ;
//
//                           public boolean isPayModSpecified(){
//                               return localPayModTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getPayMod(){
//                               return localPayMod;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param PayMod
//                               */
//                               public void setPayMod(java.lang.String param){
//                            localPayModTracker = param != null;
//                                   
//                                            this.localPayMod=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RcvBrch
//                        */
//
//                        
//                                    protected java.lang.String localRcvBrch ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRcvBrchTracker = false ;
//
//                           public boolean isRcvBrchSpecified(){
//                               return localRcvBrchTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRcvBrch(){
//                               return localRcvBrch;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RcvBrch
//                               */
//                               public void setRcvBrch(java.lang.String param){
//                            localRcvBrchTracker = param != null;
//                                   
//                                            this.localRcvBrch=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CshChnl
//                        */
//
//                        
//                                    protected java.lang.String localCshChnl ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCshChnlTracker = false ;
//
//                           public boolean isCshChnlSpecified(){
//                               return localCshChnlTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCshChnl(){
//                               return localCshChnl;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CshChnl
//                               */
//                               public void setCshChnl(java.lang.String param){
//                            localCshChnlTracker = param != null;
//                                   
//                                            this.localCshChnl=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for SbjtNo
//                        */
//
//                        
//                                    protected java.lang.String localSbjtNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localSbjtNoTracker = false ;
//
//                           public boolean isSbjtNoSpecified(){
//                               return localSbjtNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getSbjtNo(){
//                               return localSbjtNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param SbjtNo
//                               */
//                               public void setSbjtNo(java.lang.String param){
//                            localSbjtNoTracker = param != null;
//                                   
//                                            this.localSbjtNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for PayFee
//                        */
//
//                        
//                                    protected java.lang.String localPayFee ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localPayFeeTracker = false ;
//
//                           public boolean isPayFeeSpecified(){
//                               return localPayFeeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getPayFee(){
//                               return localPayFee;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param PayFee
//                               */
//                               public void setPayFee(java.lang.String param){
//                            localPayFeeTracker = param != null;
//                                   
//                                            this.localPayFee=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for AcctNo1
//                        */
//
//                        
//                                    protected java.lang.String localAcctNo1 ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAcctNo1Tracker = false ;
//
//                           public boolean isAcctNo1Specified(){
//                               return localAcctNo1Tracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAcctNo1(){
//                               return localAcctNo1;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param AcctNo1
//                               */
//                               public void setAcctNo1(java.lang.String param){
//                            localAcctNo1Tracker = param != null;
//                                   
//                                            this.localAcctNo1=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for BizCd
//                        */
//
//                        
//                                    protected java.lang.String localBizCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localBizCdTracker = false ;
//
//                           public boolean isBizCdSpecified(){
//                               return localBizCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getBizCd(){
//                               return localBizCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param BizCd
//                               */
//                               public void setBizCd(java.lang.String param){
//                            localBizCdTracker = param != null;
//                                   
//                                            this.localBizCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CrdPwd
//                        */
//
//                        
//                                    protected java.lang.String localCrdPwd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCrdPwdTracker = false ;
//
//                           public boolean isCrdPwdSpecified(){
//                               return localCrdPwdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCrdPwd(){
//                               return localCrdPwd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CrdPwd
//                               */
//                               public void setCrdPwd(java.lang.String param){
//                            localCrdPwdTracker = param != null;
//                                   
//                                            this.localCrdPwd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for DrwTyp
//                        */
//
//                        
//                                    protected java.lang.String localDrwTyp ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localDrwTypTracker = false ;
//
//                           public boolean isDrwTypSpecified(){
//                               return localDrwTypTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getDrwTyp(){
//                               return localDrwTyp;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param DrwTyp
//                               */
//                               public void setDrwTyp(java.lang.String param){
//                            localDrwTypTracker = param != null;
//                                   
//                                            this.localDrwTyp=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for VchMod
//                        */
//
//                        
//                                    protected java.lang.String localVchMod ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localVchModTracker = false ;
//
//                           public boolean isVchModSpecified(){
//                               return localVchModTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getVchMod(){
//                               return localVchMod;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param VchMod
//                               */
//                               public void setVchMod(java.lang.String param){
//                            localVchModTracker = param != null;
//                                   
//                                            this.localVchMod=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for BillNo
//                        */
//
//                        
//                                    protected java.lang.String localBillNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localBillNoTracker = false ;
//
//                           public boolean isBillNoSpecified(){
//                               return localBillNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getBillNo(){
//                               return localBillNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param BillNo
//                               */
//                               public void setBillNo(java.lang.String param){
//                            localBillNoTracker = param != null;
//                                   
//                                            this.localBillNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TranLvl
//                        */
//
//                        
//                                    protected java.lang.String localTranLvl ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTranLvlTracker = false ;
//
//                           public boolean isTranLvlSpecified(){
//                               return localTranLvlTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTranLvl(){
//                               return localTranLvl;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TranLvl
//                               */
//                               public void setTranLvl(java.lang.String param){
//                            localTranLvlTracker = param != null;
//                                   
//                                            this.localTranLvl=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for BizKind
//                        */
//
//                        
//                                    protected java.lang.String localBizKind ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localBizKindTracker = false ;
//
//                           public boolean isBizKindSpecified(){
//                               return localBizKindTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getBizKind(){
//                               return localBizKind;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param BizKind
//                               */
//                               public void setBizKind(java.lang.String param){
//                            localBizKindTracker = param != null;
//                                   
//                                            this.localBizKind=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for BizMod
//                        */
//
//                        
//                                    protected java.lang.String localBizMod ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localBizModTracker = false ;
//
//                           public boolean isBizModSpecified(){
//                               return localBizModTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getBizMod(){
//                               return localBizMod;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param BizMod
//                               */
//                               public void setBizMod(java.lang.String param){
//                            localBizModTracker = param != null;
//                                   
//                                            this.localBizMod=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for MsgIdNo
//                        */
//
//                        
//                                    protected java.lang.String localMsgIdNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localMsgIdNoTracker = false ;
//
//                           public boolean isMsgIdNoSpecified(){
//                               return localMsgIdNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getMsgIdNo(){
//                               return localMsgIdNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param MsgIdNo
//                               */
//                               public void setMsgIdNo(java.lang.String param){
//                            localMsgIdNoTracker = param != null;
//                                   
//                                            this.localMsgIdNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for DeAcctTyp
//                        */
//
//                        
//                                    protected java.lang.String localDeAcctTyp ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localDeAcctTypTracker = false ;
//
//                           public boolean isDeAcctTypSpecified(){
//                               return localDeAcctTypTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getDeAcctTyp(){
//                               return localDeAcctTyp;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param DeAcctTyp
//                               */
//                               public void setDeAcctTyp(java.lang.String param){
//                            localDeAcctTypTracker = param != null;
//                                   
//                                            this.localDeAcctTyp=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CltAcct
//                        */
//
//                        
//                                    protected java.lang.String localCltAcct ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCltAcctTracker = false ;
//
//                           public boolean isCltAcctSpecified(){
//                               return localCltAcctTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCltAcct(){
//                               return localCltAcct;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CltAcct
//                               */
//                               public void setCltAcct(java.lang.String param){
//                            localCltAcctTracker = param != null;
//                                   
//                                            this.localCltAcct=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ChagAcctNmeFif
//                        */
//
//                        
//                                    protected java.lang.String localChagAcctNmeFif ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localChagAcctNmeFifTracker = false ;
//
//                           public boolean isChagAcctNmeFifSpecified(){
//                               return localChagAcctNmeFifTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getChagAcctNmeFif(){
//                               return localChagAcctNmeFif;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ChagAcctNmeFif
//                               */
//                               public void setChagAcctNmeFif(java.lang.String param){
//                            localChagAcctNmeFifTracker = param != null;
//                                   
//                                            this.localChagAcctNmeFif=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CustMsg
//                        */
//
//                        
//                                    protected java.lang.String localCustMsg ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCustMsgTracker = false ;
//
//                           public boolean isCustMsgSpecified(){
//                               return localCustMsgTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCustMsg(){
//                               return localCustMsg;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CustMsg
//                               */
//                               public void setCustMsg(java.lang.String param){
//                            localCustMsgTracker = param != null;
//                                   
//                                            this.localCustMsg=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RcvAcctNo
//                        */
//
//                        
//                                    protected java.lang.String localRcvAcctNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRcvAcctNoTracker = false ;
//
//                           public boolean isRcvAcctNoSpecified(){
//                               return localRcvAcctNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRcvAcctNo(){
//                               return localRcvAcctNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RcvAcctNo
//                               */
//                               public void setRcvAcctNo(java.lang.String param){
//                            localRcvAcctNoTracker = param != null;
//                                   
//                                            this.localRcvAcctNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RcvAcctNme
//                        */
//
//                        
//                                    protected java.lang.String localRcvAcctNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRcvAcctNmeTracker = false ;
//
//                           public boolean isRcvAcctNmeSpecified(){
//                               return localRcvAcctNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRcvAcctNme(){
//                               return localRcvAcctNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RcvAcctNme
//                               */
//                               public void setRcvAcctNme(java.lang.String param){
//                            localRcvAcctNmeTracker = param != null;
//                                   
//                                            this.localRcvAcctNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RcvBkNo
//                        */
//
//                        
//                                    protected java.lang.String localRcvBkNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRcvBkNoTracker = false ;
//
//                           public boolean isRcvBkNoSpecified(){
//                               return localRcvBkNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRcvBkNo(){
//                               return localRcvBkNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RcvBkNo
//                               */
//                               public void setRcvBkNo(java.lang.String param){
//                            localRcvBkNoTracker = param != null;
//                                   
//                                            this.localRcvBkNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RcvBkNme
//                        */
//
//                        
//                                    protected java.lang.String localRcvBkNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRcvBkNmeTracker = false ;
//
//                           public boolean isRcvBkNmeSpecified(){
//                               return localRcvBkNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRcvBkNme(){
//                               return localRcvBkNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RcvBkNme
//                               */
//                               public void setRcvBkNme(java.lang.String param){
//                            localRcvBkNmeTracker = param != null;
//                                   
//                                            this.localRcvBkNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RcvAcctTyp
//                        */
//
//                        
//                                    protected java.lang.String localRcvAcctTyp ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRcvAcctTypTracker = false ;
//
//                           public boolean isRcvAcctTypSpecified(){
//                               return localRcvAcctTypTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRcvAcctTyp(){
//                               return localRcvAcctTyp;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RcvAcctTyp
//                               */
//                               public void setRcvAcctTyp(java.lang.String param){
//                            localRcvAcctTypTracker = param != null;
//                                   
//                                            this.localRcvAcctTyp=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for OthVchTyp
//                        */
//
//                        
//                                    protected java.lang.String localOthVchTyp ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localOthVchTypTracker = false ;
//
//                           public boolean isOthVchTypSpecified(){
//                               return localOthVchTypTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getOthVchTyp(){
//                               return localOthVchTyp;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param OthVchTyp
//                               */
//                               public void setOthVchTyp(java.lang.String param){
//                            localOthVchTypTracker = param != null;
//                                   
//                                            this.localOthVchTyp=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for VchNo
//                        */
//
//                        
//                                    protected java.lang.String localVchNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localVchNoTracker = false ;
//
//                           public boolean isVchNoSpecified(){
//                               return localVchNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getVchNo(){
//                               return localVchNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param VchNo
//                               */
//                               public void setVchNo(java.lang.String param){
//                            localVchNoTracker = param != null;
//                                   
//                                            this.localVchNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for IssBrch
//                        */
//
//                        
//                                    protected java.lang.String localIssBrch ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localIssBrchTracker = false ;
//
//                           public boolean isIssBrchSpecified(){
//                               return localIssBrchTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getIssBrch(){
//                               return localIssBrch;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param IssBrch
//                               */
//                               public void setIssBrch(java.lang.String param){
//                            localIssBrchTracker = param != null;
//                                   
//                                            this.localIssBrch=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for AcctBrchNme
//                        */
//
//                        
//                                    protected java.lang.String localAcctBrchNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAcctBrchNmeTracker = false ;
//
//                           public boolean isAcctBrchNmeSpecified(){
//                               return localAcctBrchNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAcctBrchNme(){
//                               return localAcctBrchNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param AcctBrchNme
//                               */
//                               public void setAcctBrchNme(java.lang.String param){
//                            localAcctBrchNmeTracker = param != null;
//                                   
//                                            this.localAcctBrchNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RevcFlg
//                        */
//
//                        
//                                    protected java.lang.String localRevcFlg ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localRevcFlgTracker = false ;
//
//                           public boolean isRevcFlgSpecified(){
//                               return localRevcFlgTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getRevcFlg(){
//                               return localRevcFlg;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RevcFlg
//                               */
//                               public void setRevcFlg(java.lang.String param){
//                            localRevcFlgTracker = param != null;
//                                   
//                                            this.localRevcFlg=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for AcctSeqNo1
//                        */
//
//                        
//                                    protected java.lang.String localAcctSeqNo1 ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAcctSeqNo1Tracker = false ;
//
//                           public boolean isAcctSeqNo1Specified(){
//                               return localAcctSeqNo1Tracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAcctSeqNo1(){
//                               return localAcctSeqNo1;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param AcctSeqNo1
//                               */
//                               public void setAcctSeqNo1(java.lang.String param){
//                            localAcctSeqNo1Tracker = param != null;
//                                   
//                                            this.localAcctSeqNo1=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for AnAmt
//                        */
//
//                        
//                                    protected java.lang.String localAnAmt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAnAmtTracker = false ;
//
//                           public boolean isAnAmtSpecified(){
//                               return localAnAmtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAnAmt(){
//                               return localAnAmt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param AnAmt
//                               */
//                               public void setAnAmt(java.lang.String param){
//                            localAnAmtTracker = param != null;
//                                   
//                                            this.localAnAmt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for AnBrch
//                        */
//
//                        
//                                    protected java.lang.String localAnBrch ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAnBrchTracker = false ;
//
//                           public boolean isAnBrchSpecified(){
//                               return localAnBrchTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAnBrch(){
//                               return localAnBrch;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param AnBrch
//                               */
//                               public void setAnBrch(java.lang.String param){
//                            localAnBrchTracker = param != null;
//                                   
//                                            this.localAnBrch=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for AnBrchNme
//                        */
//
//                        
//                                    protected java.lang.String localAnBrchNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAnBrchNmeTracker = false ;
//
//                           public boolean isAnBrchNmeSpecified(){
//                               return localAnBrchNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAnBrchNme(){
//                               return localAnBrchNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param AnBrchNme
//                               */
//                               public void setAnBrchNme(java.lang.String param){
//                            localAnBrchNmeTracker = param != null;
//                                   
//                                            this.localAnBrchNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for AnInAcctNo
//                        */
//
//                        
//                                    protected java.lang.String localAnInAcctNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAnInAcctNoTracker = false ;
//
//                           public boolean isAnInAcctNoSpecified(){
//                               return localAnInAcctNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAnInAcctNo(){
//                               return localAnInAcctNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param AnInAcctNo
//                               */
//                               public void setAnInAcctNo(java.lang.String param){
//                            localAnInAcctNoTracker = param != null;
//                                   
//                                            this.localAnInAcctNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CrAcctNme
//                        */
//
//                        
//                                    protected java.lang.String localCrAcctNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCrAcctNmeTracker = false ;
//
//                           public boolean isCrAcctNmeSpecified(){
//                               return localCrAcctNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCrAcctNme(){
//                               return localCrAcctNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CrAcctNme
//                               */
//                               public void setCrAcctNme(java.lang.String param){
//                            localCrAcctNmeTracker = param != null;
//                                   
//                                            this.localCrAcctNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for AcctType
//                        */
//
//                        
//                                    protected java.lang.String localAcctType ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAcctTypeTracker = false ;
//
//                           public boolean isAcctTypeSpecified(){
//                               return localAcctTypeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAcctType(){
//                               return localAcctType;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param AcctType
//                               */
//                               public void setAcctType(java.lang.String param){
//                            localAcctTypeTracker = param != null;
//                                   
//                                            this.localAcctType=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Ccy
//                        */
//
//                        
//                                    protected java.lang.String localCcy ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCcyTracker = false ;
//
//                           public boolean isCcySpecified(){
//                               return localCcyTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCcy(){
//                               return localCcy;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Ccy
//                               */
//                               public void setCcy(java.lang.String param){
//                            localCcyTracker = param != null;
//                                   
//                                            this.localCcy=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Amt
//                        */
//
//                        
//                                    protected java.lang.String localAmt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAmtTracker = false ;
//
//                           public boolean isAmtSpecified(){
//                               return localAmtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getAmt(){
//                               return localAmt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Amt
//                               */
//                               public void setAmt(java.lang.String param){
//                            localAmtTracker = param != null;
//                                   
//                                            this.localAmt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for IntFlg
//                        */
//
//                        
//                                    protected java.lang.String localIntFlg ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localIntFlgTracker = false ;
//
//                           public boolean isIntFlgSpecified(){
//                               return localIntFlgTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getIntFlg(){
//                               return localIntFlg;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param IntFlg
//                               */
//                               public void setIntFlg(java.lang.String param){
//                            localIntFlgTracker = param != null;
//                                   
//                                            this.localIntFlg=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for FlowCode
//                        */
//
//                        
//                                    protected java.lang.String localFlowCode ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localFlowCodeTracker = false ;
//
//                           public boolean isFlowCodeSpecified(){
//                               return localFlowCodeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getFlowCode(){
//                               return localFlowCode;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param FlowCode
//                               */
//                               public void setFlowCode(java.lang.String param){
//                            localFlowCodeTracker = param != null;
//                                   
//                                            this.localFlowCode=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for QryMsgId
//                        */
//
//                        
//                                    protected java.lang.String localQryMsgId ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localQryMsgIdTracker = false ;
//
//                           public boolean isQryMsgIdSpecified(){
//                               return localQryMsgIdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getQryMsgId(){
//                               return localQryMsgId;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param QryMsgId
//                               */
//                               public void setQryMsgId(java.lang.String param){
//                            localQryMsgIdTracker = param != null;
//                                   
//                                            this.localQryMsgId=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TxnType
//                        */
//
//                        
//                                    protected java.lang.String localTxnType ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTxnTypeTracker = false ;
//
//                           public boolean isTxnTypeSpecified(){
//                               return localTxnTypeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTxnType(){
//                               return localTxnType;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TxnType
//                               */
//                               public void setTxnType(java.lang.String param){
//                            localTxnTypeTracker = param != null;
//                                   
//                                            this.localTxnType=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for DePwd
//                        */
//
//                        
//                                    protected java.lang.String localDePwd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localDePwdTracker = false ;
//
//                           public boolean isDePwdSpecified(){
//                               return localDePwdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getDePwd(){
//                               return localDePwd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param DePwd
//                               */
//                               public void setDePwd(java.lang.String param){
//                            localDePwdTracker = param != null;
//                                   
//                                            this.localDePwd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CallType
//                        */
//
//                        
//                                    protected java.lang.String localCallType ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCallTypeTracker = false ;
//
//                           public boolean isCallTypeSpecified(){
//                               return localCallTypeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCallType(){
//                               return localCallType;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CallType
//                               */
//                               public void setCallType(java.lang.String param){
//                            localCallTypeTracker = param != null;
//                                   
//                                            this.localCallType=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for EntInfo
//                        */
//
//                        
//                                    protected java.lang.String localEntInfo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localEntInfoTracker = false ;
//
//                           public boolean isEntInfoSpecified(){
//                               return localEntInfoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getEntInfo(){
//                               return localEntInfo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param EntInfo
//                               */
//                               public void setEntInfo(java.lang.String param){
//                            localEntInfoTracker = param != null;
//                                   
//                                            this.localEntInfo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TxnCd
//                        */
//
//                        
//                                    protected java.lang.String localTxnCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTxnCdTracker = false ;
//
//                           public boolean isTxnCdSpecified(){
//                               return localTxnCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTxnCd(){
//                               return localTxnCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TxnCd
//                               */
//                               public void setTxnCd(java.lang.String param){
//                            localTxnCdTracker = param != null;
//                                   
//                                            this.localTxnCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for NtnltyCd
//                        */
//
//                        
//                                    protected java.lang.String localNtnltyCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localNtnltyCdTracker = false ;
//
//                           public boolean isNtnltyCdSpecified(){
//                               return localNtnltyCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getNtnltyCd(){
//                               return localNtnltyCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param NtnltyCd
//                               */
//                               public void setNtnltyCd(java.lang.String param){
//                            localNtnltyCdTracker = param != null;
//                                   
//                                            this.localNtnltyCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Type
//                        */
//
//                        
//                                    protected java.lang.String localType ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTypeTracker = false ;
//
//                           public boolean isTypeSpecified(){
//                               return localTypeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getType(){
//                               return localType;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Type
//                               */
//                               public void setType(java.lang.String param){
//                            localTypeTracker = param != null;
//                                   
//                                            this.localType=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CertTyp
//                        */
//
//                        
//                                    protected java.lang.String localCertTyp ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCertTypTracker = false ;
//
//                           public boolean isCertTypSpecified(){
//                               return localCertTypTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCertTyp(){
//                               return localCertTyp;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CertTyp
//                               */
//                               public void setCertTyp(java.lang.String param){
//                            localCertTypTracker = param != null;
//                                   
//                                            this.localCertTyp=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CertNo
//                        */
//
//                        
//                                    protected java.lang.String localCertNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCertNoTracker = false ;
//
//                           public boolean isCertNoSpecified(){
//                               return localCertNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCertNo(){
//                               return localCertNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CertNo
//                               */
//                               public void setCertNo(java.lang.String param){
//                            localCertNoTracker = param != null;
//                                   
//                                            this.localCertNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for BrkrIssInstrmtCatgy
//                        */
//
//                        
//                                    protected java.lang.String localBrkrIssInstrmtCatgy ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localBrkrIssInstrmtCatgyTracker = false ;
//
//                           public boolean isBrkrIssInstrmtCatgySpecified(){
//                               return localBrkrIssInstrmtCatgyTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getBrkrIssInstrmtCatgy(){
//                               return localBrkrIssInstrmtCatgy;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param BrkrIssInstrmtCatgy
//                               */
//                               public void setBrkrIssInstrmtCatgy(java.lang.String param){
//                            localBrkrIssInstrmtCatgyTracker = param != null;
//                                   
//                                            this.localBrkrIssInstrmtCatgy=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CntaTel
//                        */
//
//                        
//                                    protected java.lang.String localCntaTel ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCntaTelTracker = false ;
//
//                           public boolean isCntaTelSpecified(){
//                               return localCntaTelTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCntaTel(){
//                               return localCntaTel;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CntaTel
//                               */
//                               public void setCntaTel(java.lang.String param){
//                            localCntaTelTracker = param != null;
//                                   
//                                            this.localCntaTel=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for PayAcctNme
//                        */
//
//                        
//                                    protected java.lang.String localPayAcctNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localPayAcctNmeTracker = false ;
//
//                           public boolean isPayAcctNmeSpecified(){
//                               return localPayAcctNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getPayAcctNme(){
//                               return localPayAcctNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param PayAcctNme
//                               */
//                               public void setPayAcctNme(java.lang.String param){
//                            localPayAcctNmeTracker = param != null;
//                                   
//                                            this.localPayAcctNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Phone
//                        */
//
//                        
//                                    protected java.lang.String localPhone ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localPhoneTracker = false ;
//
//                           public boolean isPhoneSpecified(){
//                               return localPhoneTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getPhone(){
//                               return localPhone;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Phone
//                               */
//                               public void setPhone(java.lang.String param){
//                            localPhoneTracker = param != null;
//                                   
//                                            this.localPhone=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Area
//                        */
//
//                        
//                                    protected java.lang.String localArea ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localAreaTracker = false ;
//
//                           public boolean isAreaSpecified(){
//                               return localAreaTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getArea(){
//                               return localArea;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Area
//                               */
//                               public void setArea(java.lang.String param){
//                            localAreaTracker = param != null;
//                                   
//                                            this.localArea=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for LawyerCertNo
//                        */
//
//                        
//                                    protected java.lang.String localLawyerCertNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localLawyerCertNoTracker = false ;
//
//                           public boolean isLawyerCertNoSpecified(){
//                               return localLawyerCertNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getLawyerCertNo(){
//                               return localLawyerCertNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param LawyerCertNo
//                               */
//                               public void setLawyerCertNo(java.lang.String param){
//                            localLawyerCertNoTracker = param != null;
//                                   
//                                            this.localLawyerCertNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for LegaCertNo
//                        */
//
//                        
//                                    protected java.lang.String localLegaCertNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localLegaCertNoTracker = false ;
//
//                           public boolean isLegaCertNoSpecified(){
//                               return localLegaCertNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getLegaCertNo(){
//                               return localLegaCertNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param LegaCertNo
//                               */
//                               public void setLegaCertNo(java.lang.String param){
//                            localLegaCertNoTracker = param != null;
//                                   
//                                            this.localLegaCertNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for IssBrchNo
//                        */
//
//                        
//                                    protected java.lang.String localIssBrchNo ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localIssBrchNoTracker = false ;
//
//                           public boolean isIssBrchNoSpecified(){
//                               return localIssBrchNoTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getIssBrchNo(){
//                               return localIssBrchNo;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param IssBrchNo
//                               */
//                               public void setIssBrchNo(java.lang.String param){
//                            localIssBrchNoTracker = param != null;
//                                   
//                                            this.localIssBrchNo=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ProjExApAuth
//                        */
//
//                        
//                                    protected java.lang.String localProjExApAuth ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localProjExApAuthTracker = false ;
//
//                           public boolean isProjExApAuthSpecified(){
//                               return localProjExApAuthTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getProjExApAuth(){
//                               return localProjExApAuth;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ProjExApAuth
//                               */
//                               public void setProjExApAuth(java.lang.String param){
//                            localProjExApAuthTracker = param != null;
//                                   
//                                            this.localProjExApAuth=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for QryAcctNme
//                        */
//
//                        
//                                    protected java.lang.String localQryAcctNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localQryAcctNmeTracker = false ;
//
//                           public boolean isQryAcctNmeSpecified(){
//                               return localQryAcctNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getQryAcctNme(){
//                               return localQryAcctNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param QryAcctNme
//                               */
//                               public void setQryAcctNme(java.lang.String param){
//                            localQryAcctNmeTracker = param != null;
//                                   
//                                            this.localQryAcctNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for TcktBkNme
//                        */
//
//                        
//                                    protected java.lang.String localTcktBkNme ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTcktBkNmeTracker = false ;
//
//                           public boolean isTcktBkNmeSpecified(){
//                               return localTcktBkNmeTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTcktBkNme(){
//                               return localTcktBkNme;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param TcktBkNme
//                               */
//                               public void setTcktBkNme(java.lang.String param){
//                            localTcktBkNmeTracker = param != null;
//                                   
//                                            this.localTcktBkNme=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ApplAmt
//                        */
//
//                        
//                                    protected java.lang.String localApplAmt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localApplAmtTracker = false ;
//
//                           public boolean isApplAmtSpecified(){
//                               return localApplAmtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getApplAmt(){
//                               return localApplAmt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ApplAmt
//                               */
//                               public void setApplAmt(java.lang.String param){
//                            localApplAmtTracker = param != null;
//                                   
//                                            this.localApplAmt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for BkngAmt
//                        */
//
//                        
//                                    protected java.lang.String localBkngAmt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localBkngAmtTracker = false ;
//
//                           public boolean isBkngAmtSpecified(){
//                               return localBkngAmtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getBkngAmt(){
//                               return localBkngAmt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param BkngAmt
//                               */
//                               public void setBkngAmt(java.lang.String param){
//                            localBkngAmtTracker = param != null;
//                                   
//                                            this.localBkngAmt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for ChagAmt
//                        */
//
//                        
//                                    protected java.lang.String localChagAmt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localChagAmtTracker = false ;
//
//                           public boolean isChagAmtSpecified(){
//                               return localChagAmtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getChagAmt(){
//                               return localChagAmt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param ChagAmt
//                               */
//                               public void setChagAmt(java.lang.String param){
//                            localChagAmtTracker = param != null;
//                                   
//                                            this.localChagAmt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CshAmt
//                        */
//
//                        
//                                    protected java.lang.String localCshAmt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCshAmtTracker = false ;
//
//                           public boolean isCshAmtSpecified(){
//                               return localCshAmtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCshAmt(){
//                               return localCshAmt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CshAmt
//                               */
//                               public void setCshAmt(java.lang.String param){
//                            localCshAmtTracker = param != null;
//                                   
//                                            this.localCshAmt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CshStAmt
//                        */
//
//                        
//                                    protected java.lang.String localCshStAmt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCshStAmtTracker = false ;
//
//                           public boolean isCshStAmtSpecified(){
//                               return localCshStAmtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCshStAmt(){
//                               return localCshStAmt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CshStAmt
//                               */
//                               public void setCshStAmt(java.lang.String param){
//                            localCshStAmtTracker = param != null;
//                                   
//                                            this.localCshStAmt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Date
//                        */
//
//                        
//                                    protected java.lang.String localDate ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localDateTracker = false ;
//
//                           public boolean isDateSpecified(){
//                               return localDateTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getDate(){
//                               return localDate;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Date
//                               */
//                               public void setDate(java.lang.String param){
//                            localDateTracker = param != null;
//                                   
//                                            this.localDate=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for IntAmt
//                        */
//
//                        
//                                    protected java.lang.String localIntAmt ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localIntAmtTracker = false ;
//
//                           public boolean isIntAmtSpecified(){
//                               return localIntAmtTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getIntAmt(){
//                               return localIntAmt;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param IntAmt
//                               */
//                               public void setIntAmt(java.lang.String param){
//                            localIntAmtTracker = param != null;
//                                   
//                                            this.localIntAmt=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for Term
//                        */
//
//                        
//                                    protected java.lang.String localTerm ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localTermTracker = false ;
//
//                           public boolean isTermSpecified(){
//                               return localTermTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getTerm(){
//                               return localTerm;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param Term
//                               */
//                               public void setTerm(java.lang.String param){
//                            localTermTracker = param != null;
//                                   
//                                            this.localTerm=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CrSecTrk
//                        */
//
//                        
//                                    protected java.lang.String localCrSecTrk ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCrSecTrkTracker = false ;
//
//                           public boolean isCrSecTrkSpecified(){
//                               return localCrSecTrkTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCrSecTrk(){
//                               return localCrSecTrk;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CrSecTrk
//                               */
//                               public void setCrSecTrk(java.lang.String param){
//                            localCrSecTrkTracker = param != null;
//                                   
//                                            this.localCrSecTrk=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for CrThdTrk
//                        */
//
//                        
//                                    protected java.lang.String localCrThdTrk ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localCrThdTrkTracker = false ;
//
//                           public boolean isCrThdTrkSpecified(){
//                               return localCrThdTrkTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getCrThdTrk(){
//                               return localCrThdTrk;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param CrThdTrk
//                               */
//                               public void setCrThdTrk(java.lang.String param){
//                            localCrThdTrkTracker = param != null;
//                                   
//                                            this.localCrThdTrk=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for DevCd
//                        */
//
//                        
//                                    protected java.lang.String localDevCd ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localDevCdTracker = false ;
//
//                           public boolean isDevCdSpecified(){
//                               return localDevCdTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getDevCd(){
//                               return localDevCd;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param DevCd
//                               */
//                               public void setDevCd(java.lang.String param){
//                            localDevCdTracker = param != null;
//                                   
//                                            this.localDevCd=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for MsgFlg
//                        */
//
//                        
//                                    protected java.lang.String localMsgFlg ;
//                                
//                           /*  This tracker boolean wil be used to detect whether the user called the set method
//                          *   for this attribute. It will be used to determine whether to include this field
//                           *   in the serialized XML
//                           */
//                           protected boolean localMsgFlgTracker = false ;
//
//                           public boolean isMsgFlgSpecified(){
//                               return localMsgFlgTracker;
//                           }
//
//                           
//
//                           /**
//                           * Auto generated getter method
//                           * @return java.lang.String
//                           */
//                           public  java.lang.String getMsgFlg(){
//                               return localMsgFlg;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param MsgFlg
//                               */
//                               public void setMsgFlg(java.lang.String param){
//                            localMsgFlgTracker = param != null;
//                                   
//                                            this.localMsgFlg=param;
//                                    
//
//                               }
//                            
//
//     
//     
//        /**
//        *
//        * @param parentQName
//        * @param factory
//        * @return org.apache.axiom.om.OMElement
//        */
//       public org.apache.axiom.om.OMElement getOMElement (
//               final javax.xml.namespace.QName parentQName,
//               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{
//
//
//        
//               org.apache.axiom.om.OMDataSource dataSource =
//                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
//               return factory.createOMElement(dataSource,parentQName);
//            
//        }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter)
//                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//                           serialize(parentQName,xmlWriter,false);
//         }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                               javax.xml.stream.XMLStreamWriter xmlWriter,
//                               boolean serializeType)
//            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//            
//                
//
//
//                java.lang.String prefix = null;
//                java.lang.String namespace = null;
//                
//
//                    prefix = parentQName.getPrefix();
//                    namespace = parentQName.getNamespaceURI();
//                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
//                
//                  if (serializeType){
//               
//
//                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adtec.com.cn");
//                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           namespacePrefix+":RequestBody",
//                           xmlWriter);
//                   } else {
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           "RequestBody",
//                           xmlWriter);
//                   }
//
//               
//                   }
//                if (localChannelTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Channel", xmlWriter);
//                             
//
//                                          if (localChannel==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Channel cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localChannel);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localSysCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "SysCd", xmlWriter);
//                             
//
//                                          if (localSysCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("SysCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localSysCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTranTypeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TranType", xmlWriter);
//                             
//
//                                          if (localTranType==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TranType cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTranType);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localPrdCodeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "PrdCode", xmlWriter);
//                             
//
//                                          if (localPrdCode==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("PrdCode cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localPrdCode);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localFlagTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Flag", xmlWriter);
//                             
//
//                                          if (localFlag==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Flag cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localFlag);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAttchTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Attch", xmlWriter);
//                             
//
//                                          if (localAttch==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Attch cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAttch);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localMemoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Memo", xmlWriter);
//                             
//
//                                          if (localMemo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Memo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localMemo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localDecAttchTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "DecAttch", xmlWriter);
//                             
//
//                                          if (localDecAttch==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("DecAttch cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localDecAttch);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRmrkTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Rmrk", xmlWriter);
//                             
//
//                                          if (localRmrk==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Rmrk cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRmrk);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localIssRmrkTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "IssRmrk", xmlWriter);
//                             
//
//                                          if (localIssRmrk==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("IssRmrk cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localIssRmrk);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localQryTypTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "QryTyp", xmlWriter);
//                             
//
//                                          if (localQryTyp==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("QryTyp cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localQryTyp);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localPayModTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "PayMod", xmlWriter);
//                             
//
//                                          if (localPayMod==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("PayMod cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localPayMod);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRcvBrchTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RcvBrch", xmlWriter);
//                             
//
//                                          if (localRcvBrch==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RcvBrch cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRcvBrch);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCshChnlTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CshChnl", xmlWriter);
//                             
//
//                                          if (localCshChnl==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CshChnl cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCshChnl);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localSbjtNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "SbjtNo", xmlWriter);
//                             
//
//                                          if (localSbjtNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("SbjtNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localSbjtNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localPayFeeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "PayFee", xmlWriter);
//                             
//
//                                          if (localPayFee==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("PayFee cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localPayFee);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAcctNo1Tracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "AcctNo1", xmlWriter);
//                             
//
//                                          if (localAcctNo1==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("AcctNo1 cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAcctNo1);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localBizCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "BizCd", xmlWriter);
//                             
//
//                                          if (localBizCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("BizCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localBizCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCrdPwdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CrdPwd", xmlWriter);
//                             
//
//                                          if (localCrdPwd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CrdPwd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCrdPwd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localDrwTypTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "DrwTyp", xmlWriter);
//                             
//
//                                          if (localDrwTyp==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("DrwTyp cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localDrwTyp);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localVchModTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "VchMod", xmlWriter);
//                             
//
//                                          if (localVchMod==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("VchMod cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localVchMod);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localBillNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "BillNo", xmlWriter);
//                             
//
//                                          if (localBillNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("BillNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localBillNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTranLvlTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TranLvl", xmlWriter);
//                             
//
//                                          if (localTranLvl==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TranLvl cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTranLvl);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localBizKindTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "BizKind", xmlWriter);
//                             
//
//                                          if (localBizKind==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("BizKind cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localBizKind);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localBizModTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "BizMod", xmlWriter);
//                             
//
//                                          if (localBizMod==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("BizMod cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localBizMod);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localMsgIdNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "MsgIdNo", xmlWriter);
//                             
//
//                                          if (localMsgIdNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("MsgIdNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localMsgIdNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localDeAcctTypTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "DeAcctTyp", xmlWriter);
//                             
//
//                                          if (localDeAcctTyp==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("DeAcctTyp cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localDeAcctTyp);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCltAcctTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CltAcct", xmlWriter);
//                             
//
//                                          if (localCltAcct==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CltAcct cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCltAcct);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localChagAcctNmeFifTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ChagAcctNmeFif", xmlWriter);
//                             
//
//                                          if (localChagAcctNmeFif==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ChagAcctNmeFif cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localChagAcctNmeFif);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCustMsgTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CustMsg", xmlWriter);
//                             
//
//                                          if (localCustMsg==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CustMsg cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCustMsg);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRcvAcctNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RcvAcctNo", xmlWriter);
//                             
//
//                                          if (localRcvAcctNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RcvAcctNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRcvAcctNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRcvAcctNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RcvAcctNme", xmlWriter);
//                             
//
//                                          if (localRcvAcctNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RcvAcctNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRcvAcctNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRcvBkNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RcvBkNo", xmlWriter);
//                             
//
//                                          if (localRcvBkNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RcvBkNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRcvBkNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRcvBkNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RcvBkNme", xmlWriter);
//                             
//
//                                          if (localRcvBkNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RcvBkNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRcvBkNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRcvAcctTypTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RcvAcctTyp", xmlWriter);
//                             
//
//                                          if (localRcvAcctTyp==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RcvAcctTyp cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRcvAcctTyp);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localOthVchTypTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "OthVchTyp", xmlWriter);
//                             
//
//                                          if (localOthVchTyp==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("OthVchTyp cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localOthVchTyp);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localVchNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "VchNo", xmlWriter);
//                             
//
//                                          if (localVchNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("VchNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localVchNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localIssBrchTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "IssBrch", xmlWriter);
//                             
//
//                                          if (localIssBrch==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("IssBrch cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localIssBrch);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAcctBrchNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "AcctBrchNme", xmlWriter);
//                             
//
//                                          if (localAcctBrchNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("AcctBrchNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAcctBrchNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localRevcFlgTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "RevcFlg", xmlWriter);
//                             
//
//                                          if (localRevcFlg==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("RevcFlg cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localRevcFlg);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAcctSeqNo1Tracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "AcctSeqNo1", xmlWriter);
//                             
//
//                                          if (localAcctSeqNo1==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("AcctSeqNo1 cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAcctSeqNo1);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAnAmtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "AnAmt", xmlWriter);
//                             
//
//                                          if (localAnAmt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("AnAmt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAnAmt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAnBrchTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "AnBrch", xmlWriter);
//                             
//
//                                          if (localAnBrch==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("AnBrch cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAnBrch);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAnBrchNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "AnBrchNme", xmlWriter);
//                             
//
//                                          if (localAnBrchNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("AnBrchNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAnBrchNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAnInAcctNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "AnInAcctNo", xmlWriter);
//                             
//
//                                          if (localAnInAcctNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("AnInAcctNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAnInAcctNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCrAcctNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CrAcctNme", xmlWriter);
//                             
//
//                                          if (localCrAcctNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CrAcctNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCrAcctNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAcctTypeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "AcctType", xmlWriter);
//                             
//
//                                          if (localAcctType==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("AcctType cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAcctType);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCcyTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Ccy", xmlWriter);
//                             
//
//                                          if (localCcy==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Ccy cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCcy);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAmtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Amt", xmlWriter);
//                             
//
//                                          if (localAmt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Amt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localAmt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localIntFlgTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "IntFlg", xmlWriter);
//                             
//
//                                          if (localIntFlg==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("IntFlg cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localIntFlg);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localFlowCodeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "FlowCode", xmlWriter);
//                             
//
//                                          if (localFlowCode==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("FlowCode cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localFlowCode);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localQryMsgIdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "QryMsgId", xmlWriter);
//                             
//
//                                          if (localQryMsgId==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("QryMsgId cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localQryMsgId);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTxnTypeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TxnType", xmlWriter);
//                             
//
//                                          if (localTxnType==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TxnType cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTxnType);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localDePwdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "DePwd", xmlWriter);
//                             
//
//                                          if (localDePwd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("DePwd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localDePwd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCallTypeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CallType", xmlWriter);
//                             
//
//                                          if (localCallType==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CallType cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCallType);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localEntInfoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "EntInfo", xmlWriter);
//                             
//
//                                          if (localEntInfo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("EntInfo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localEntInfo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTxnCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TxnCd", xmlWriter);
//                             
//
//                                          if (localTxnCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TxnCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTxnCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localNtnltyCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "NtnltyCd", xmlWriter);
//                             
//
//                                          if (localNtnltyCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("NtnltyCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localNtnltyCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTypeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Type", xmlWriter);
//                             
//
//                                          if (localType==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Type cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localType);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCertTypTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CertTyp", xmlWriter);
//                             
//
//                                          if (localCertTyp==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CertTyp cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCertTyp);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCertNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CertNo", xmlWriter);
//                             
//
//                                          if (localCertNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CertNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCertNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localBrkrIssInstrmtCatgyTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "BrkrIssInstrmtCatgy", xmlWriter);
//                             
//
//                                          if (localBrkrIssInstrmtCatgy==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("BrkrIssInstrmtCatgy cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localBrkrIssInstrmtCatgy);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCntaTelTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CntaTel", xmlWriter);
//                             
//
//                                          if (localCntaTel==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CntaTel cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCntaTel);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localPayAcctNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "PayAcctNme", xmlWriter);
//                             
//
//                                          if (localPayAcctNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("PayAcctNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localPayAcctNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localPhoneTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Phone", xmlWriter);
//                             
//
//                                          if (localPhone==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Phone cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localPhone);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localAreaTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Area", xmlWriter);
//                             
//
//                                          if (localArea==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Area cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localArea);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localLawyerCertNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "LawyerCertNo", xmlWriter);
//                             
//
//                                          if (localLawyerCertNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("LawyerCertNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localLawyerCertNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localLegaCertNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "LegaCertNo", xmlWriter);
//                             
//
//                                          if (localLegaCertNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("LegaCertNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localLegaCertNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localIssBrchNoTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "IssBrchNo", xmlWriter);
//                             
//
//                                          if (localIssBrchNo==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("IssBrchNo cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localIssBrchNo);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localProjExApAuthTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ProjExApAuth", xmlWriter);
//                             
//
//                                          if (localProjExApAuth==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ProjExApAuth cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localProjExApAuth);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localQryAcctNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "QryAcctNme", xmlWriter);
//                             
//
//                                          if (localQryAcctNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("QryAcctNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localQryAcctNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTcktBkNmeTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "TcktBkNme", xmlWriter);
//                             
//
//                                          if (localTcktBkNme==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("TcktBkNme cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTcktBkNme);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localApplAmtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ApplAmt", xmlWriter);
//                             
//
//                                          if (localApplAmt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ApplAmt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localApplAmt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localBkngAmtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "BkngAmt", xmlWriter);
//                             
//
//                                          if (localBkngAmt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("BkngAmt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localBkngAmt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localChagAmtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "ChagAmt", xmlWriter);
//                             
//
//                                          if (localChagAmt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("ChagAmt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localChagAmt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCshAmtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CshAmt", xmlWriter);
//                             
//
//                                          if (localCshAmt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CshAmt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCshAmt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCshStAmtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CshStAmt", xmlWriter);
//                             
//
//                                          if (localCshStAmt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CshStAmt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCshStAmt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localDateTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Date", xmlWriter);
//                             
//
//                                          if (localDate==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Date cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localDate);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localIntAmtTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "IntAmt", xmlWriter);
//                             
//
//                                          if (localIntAmt==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("IntAmt cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localIntAmt);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localTermTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "Term", xmlWriter);
//                             
//
//                                          if (localTerm==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("Term cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localTerm);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCrSecTrkTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CrSecTrk", xmlWriter);
//                             
//
//                                          if (localCrSecTrk==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CrSecTrk cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCrSecTrk);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localCrThdTrkTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "CrThdTrk", xmlWriter);
//                             
//
//                                          if (localCrThdTrk==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("CrThdTrk cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localCrThdTrk);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localDevCdTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "DevCd", xmlWriter);
//                             
//
//                                          if (localDevCd==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("DevCd cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localDevCd);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             } if (localMsgFlgTracker){
//                                    namespace = "";
//                                    writeStartElement(null, namespace, "MsgFlg", xmlWriter);
//                             
//
//                                          if (localMsgFlg==null){
//                                              // write the nil attribute
//                                              
//                                                     throw new org.apache.axis2.databinding.ADBException("MsgFlg cannot be null!!");
//                                                  
//                                          }else{
//
//                                        
//                                                   xmlWriter.writeCharacters(localMsgFlg);
//                                            
//                                          }
//                                    
//                                   xmlWriter.writeEndElement();
//                             }
//                    xmlWriter.writeEndElement();
//               
//
//        }
//
//        private static java.lang.String generatePrefix(java.lang.String namespace) {
//            if(namespace.equals("http://www.adtec.com.cn")){
//                return "ns1";
//            }
//            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//        }
//
//        /**
//         * Utility method to write an element start tag.
//         */
//        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
//            if (writerPrefix != null) {
//                xmlWriter.writeStartElement(namespace, localPart);
//            } else {
//                if (namespace.length() == 0) {
//                    prefix = "";
//                } else if (prefix == null) {
//                    prefix = generatePrefix(namespace);
//                }
//
//                xmlWriter.writeStartElement(prefix, localPart, namespace);
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//        }
//        
//        /**
//         * Util method to write an attribute with the ns prefix
//         */
//        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (xmlWriter.getPrefix(namespace) == null) {
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            xmlWriter.writeAttribute(namespace,attName,attValue);
//        }
//
//        /**
//         * Util method to write an attribute without the ns prefix
//         */
//        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (namespace.equals("")) {
//                xmlWriter.writeAttribute(attName,attValue);
//            } else {
//                registerPrefix(xmlWriter, namespace);
//                xmlWriter.writeAttribute(namespace,attName,attValue);
//            }
//        }
//
//
//           /**
//             * Util method to write an attribute without the ns prefix
//             */
//            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
//                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//                java.lang.String attributeNamespace = qname.getNamespaceURI();
//                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
//                if (attributePrefix == null) {
//                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
//                }
//                java.lang.String attributeValue;
//                if (attributePrefix.trim().length() > 0) {
//                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
//                } else {
//                    attributeValue = qname.getLocalPart();
//                }
//
//                if (namespace.equals("")) {
//                    xmlWriter.writeAttribute(attName, attributeValue);
//                } else {
//                    registerPrefix(xmlWriter, namespace);
//                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
//                }
//            }
//        /**
//         *  method to handle Qnames
//         */
//
//        private void writeQName(javax.xml.namespace.QName qname,
//                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String namespaceURI = qname.getNamespaceURI();
//            if (namespaceURI != null) {
//                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
//                if (prefix == null) {
//                    prefix = generatePrefix(namespaceURI);
//                    xmlWriter.writeNamespace(prefix, namespaceURI);
//                    xmlWriter.setPrefix(prefix,namespaceURI);
//                }
//
//                if (prefix.trim().length() > 0){
//                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                } else {
//                    // i.e this is the default namespace
//                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                }
//
//            } else {
//                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//            }
//        }
//
//        private void writeQNames(javax.xml.namespace.QName[] qnames,
//                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//            if (qnames != null) {
//                // we have to store this data until last moment since it is not possible to write any
//                // namespace data after writing the charactor data
//                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
//                java.lang.String namespaceURI = null;
//                java.lang.String prefix = null;
//
//                for (int i = 0; i < qnames.length; i++) {
//                    if (i > 0) {
//                        stringToWrite.append(" ");
//                    }
//                    namespaceURI = qnames[i].getNamespaceURI();
//                    if (namespaceURI != null) {
//                        prefix = xmlWriter.getPrefix(namespaceURI);
//                        if ((prefix == null) || (prefix.length() == 0)) {
//                            prefix = generatePrefix(namespaceURI);
//                            xmlWriter.writeNamespace(prefix, namespaceURI);
//                            xmlWriter.setPrefix(prefix,namespaceURI);
//                        }
//
//                        if (prefix.trim().length() > 0){
//                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        } else {
//                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        }
//                    } else {
//                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                    }
//                }
//                xmlWriter.writeCharacters(stringToWrite.toString());
//            }
//
//        }
//
//
//        /**
//         * Register a namespace prefix
//         */
//        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
//            java.lang.String prefix = xmlWriter.getPrefix(namespace);
//            if (prefix == null) {
//                prefix = generatePrefix(namespace);
//                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
//                while (true) {
//                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
//                    if (uri == null || uri.length() == 0) {
//                        break;
//                    }
//                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//                }
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            return prefix;
//        }
//
//
//  
//        /**
//        * databinding method to get an XML representation of this object
//        *
//        */
//        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
//                    throws org.apache.axis2.databinding.ADBException{
//
//
//        
//                 java.util.ArrayList elementList = new java.util.ArrayList();
//                 java.util.ArrayList attribList = new java.util.ArrayList();
//
//                 if (localChannelTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Channel"));
//                                 
//                                        if (localChannel != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChannel));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Channel cannot be null!!");
//                                        }
//                                    } if (localSysCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "SysCd"));
//                                 
//                                        if (localSysCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSysCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("SysCd cannot be null!!");
//                                        }
//                                    } if (localTranTypeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TranType"));
//                                 
//                                        if (localTranType != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTranType));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TranType cannot be null!!");
//                                        }
//                                    } if (localPrdCodeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "PrdCode"));
//                                 
//                                        if (localPrdCode != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrdCode));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("PrdCode cannot be null!!");
//                                        }
//                                    } if (localFlagTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Flag"));
//                                 
//                                        if (localFlag != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFlag));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Flag cannot be null!!");
//                                        }
//                                    } if (localAttchTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Attch"));
//                                 
//                                        if (localAttch != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAttch));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Attch cannot be null!!");
//                                        }
//                                    } if (localMemoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Memo"));
//                                 
//                                        if (localMemo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMemo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Memo cannot be null!!");
//                                        }
//                                    } if (localDecAttchTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "DecAttch"));
//                                 
//                                        if (localDecAttch != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDecAttch));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("DecAttch cannot be null!!");
//                                        }
//                                    } if (localRmrkTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Rmrk"));
//                                 
//                                        if (localRmrk != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRmrk));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Rmrk cannot be null!!");
//                                        }
//                                    } if (localIssRmrkTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "IssRmrk"));
//                                 
//                                        if (localIssRmrk != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssRmrk));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("IssRmrk cannot be null!!");
//                                        }
//                                    } if (localQryTypTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "QryTyp"));
//                                 
//                                        if (localQryTyp != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localQryTyp));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("QryTyp cannot be null!!");
//                                        }
//                                    } if (localPayModTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "PayMod"));
//                                 
//                                        if (localPayMod != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPayMod));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("PayMod cannot be null!!");
//                                        }
//                                    } if (localRcvBrchTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RcvBrch"));
//                                 
//                                        if (localRcvBrch != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRcvBrch));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RcvBrch cannot be null!!");
//                                        }
//                                    } if (localCshChnlTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CshChnl"));
//                                 
//                                        if (localCshChnl != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCshChnl));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CshChnl cannot be null!!");
//                                        }
//                                    } if (localSbjtNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "SbjtNo"));
//                                 
//                                        if (localSbjtNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSbjtNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("SbjtNo cannot be null!!");
//                                        }
//                                    } if (localPayFeeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "PayFee"));
//                                 
//                                        if (localPayFee != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPayFee));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("PayFee cannot be null!!");
//                                        }
//                                    } if (localAcctNo1Tracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "AcctNo1"));
//                                 
//                                        if (localAcctNo1 != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctNo1));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("AcctNo1 cannot be null!!");
//                                        }
//                                    } if (localBizCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "BizCd"));
//                                 
//                                        if (localBizCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBizCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("BizCd cannot be null!!");
//                                        }
//                                    } if (localCrdPwdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CrdPwd"));
//                                 
//                                        if (localCrdPwd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCrdPwd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CrdPwd cannot be null!!");
//                                        }
//                                    } if (localDrwTypTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "DrwTyp"));
//                                 
//                                        if (localDrwTyp != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDrwTyp));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("DrwTyp cannot be null!!");
//                                        }
//                                    } if (localVchModTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "VchMod"));
//                                 
//                                        if (localVchMod != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVchMod));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("VchMod cannot be null!!");
//                                        }
//                                    } if (localBillNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "BillNo"));
//                                 
//                                        if (localBillNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBillNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("BillNo cannot be null!!");
//                                        }
//                                    } if (localTranLvlTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TranLvl"));
//                                 
//                                        if (localTranLvl != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTranLvl));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TranLvl cannot be null!!");
//                                        }
//                                    } if (localBizKindTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "BizKind"));
//                                 
//                                        if (localBizKind != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBizKind));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("BizKind cannot be null!!");
//                                        }
//                                    } if (localBizModTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "BizMod"));
//                                 
//                                        if (localBizMod != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBizMod));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("BizMod cannot be null!!");
//                                        }
//                                    } if (localMsgIdNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "MsgIdNo"));
//                                 
//                                        if (localMsgIdNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMsgIdNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("MsgIdNo cannot be null!!");
//                                        }
//                                    } if (localDeAcctTypTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "DeAcctTyp"));
//                                 
//                                        if (localDeAcctTyp != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDeAcctTyp));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("DeAcctTyp cannot be null!!");
//                                        }
//                                    } if (localCltAcctTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CltAcct"));
//                                 
//                                        if (localCltAcct != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCltAcct));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CltAcct cannot be null!!");
//                                        }
//                                    } if (localChagAcctNmeFifTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ChagAcctNmeFif"));
//                                 
//                                        if (localChagAcctNmeFif != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChagAcctNmeFif));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ChagAcctNmeFif cannot be null!!");
//                                        }
//                                    } if (localCustMsgTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CustMsg"));
//                                 
//                                        if (localCustMsg != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCustMsg));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CustMsg cannot be null!!");
//                                        }
//                                    } if (localRcvAcctNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RcvAcctNo"));
//                                 
//                                        if (localRcvAcctNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRcvAcctNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RcvAcctNo cannot be null!!");
//                                        }
//                                    } if (localRcvAcctNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RcvAcctNme"));
//                                 
//                                        if (localRcvAcctNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRcvAcctNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RcvAcctNme cannot be null!!");
//                                        }
//                                    } if (localRcvBkNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RcvBkNo"));
//                                 
//                                        if (localRcvBkNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRcvBkNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RcvBkNo cannot be null!!");
//                                        }
//                                    } if (localRcvBkNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RcvBkNme"));
//                                 
//                                        if (localRcvBkNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRcvBkNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RcvBkNme cannot be null!!");
//                                        }
//                                    } if (localRcvAcctTypTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RcvAcctTyp"));
//                                 
//                                        if (localRcvAcctTyp != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRcvAcctTyp));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RcvAcctTyp cannot be null!!");
//                                        }
//                                    } if (localOthVchTypTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "OthVchTyp"));
//                                 
//                                        if (localOthVchTyp != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOthVchTyp));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("OthVchTyp cannot be null!!");
//                                        }
//                                    } if (localVchNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "VchNo"));
//                                 
//                                        if (localVchNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localVchNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("VchNo cannot be null!!");
//                                        }
//                                    } if (localIssBrchTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "IssBrch"));
//                                 
//                                        if (localIssBrch != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssBrch));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("IssBrch cannot be null!!");
//                                        }
//                                    } if (localAcctBrchNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "AcctBrchNme"));
//                                 
//                                        if (localAcctBrchNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctBrchNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("AcctBrchNme cannot be null!!");
//                                        }
//                                    } if (localRevcFlgTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RevcFlg"));
//                                 
//                                        if (localRevcFlg != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRevcFlg));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("RevcFlg cannot be null!!");
//                                        }
//                                    } if (localAcctSeqNo1Tracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "AcctSeqNo1"));
//                                 
//                                        if (localAcctSeqNo1 != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctSeqNo1));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("AcctSeqNo1 cannot be null!!");
//                                        }
//                                    } if (localAnAmtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "AnAmt"));
//                                 
//                                        if (localAnAmt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAnAmt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("AnAmt cannot be null!!");
//                                        }
//                                    } if (localAnBrchTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "AnBrch"));
//                                 
//                                        if (localAnBrch != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAnBrch));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("AnBrch cannot be null!!");
//                                        }
//                                    } if (localAnBrchNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "AnBrchNme"));
//                                 
//                                        if (localAnBrchNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAnBrchNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("AnBrchNme cannot be null!!");
//                                        }
//                                    } if (localAnInAcctNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "AnInAcctNo"));
//                                 
//                                        if (localAnInAcctNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAnInAcctNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("AnInAcctNo cannot be null!!");
//                                        }
//                                    } if (localCrAcctNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CrAcctNme"));
//                                 
//                                        if (localCrAcctNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCrAcctNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CrAcctNme cannot be null!!");
//                                        }
//                                    } if (localAcctTypeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "AcctType"));
//                                 
//                                        if (localAcctType != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAcctType));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("AcctType cannot be null!!");
//                                        }
//                                    } if (localCcyTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Ccy"));
//                                 
//                                        if (localCcy != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCcy));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Ccy cannot be null!!");
//                                        }
//                                    } if (localAmtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Amt"));
//                                 
//                                        if (localAmt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAmt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Amt cannot be null!!");
//                                        }
//                                    } if (localIntFlgTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "IntFlg"));
//                                 
//                                        if (localIntFlg != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIntFlg));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("IntFlg cannot be null!!");
//                                        }
//                                    } if (localFlowCodeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "FlowCode"));
//                                 
//                                        if (localFlowCode != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFlowCode));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("FlowCode cannot be null!!");
//                                        }
//                                    } if (localQryMsgIdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "QryMsgId"));
//                                 
//                                        if (localQryMsgId != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localQryMsgId));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("QryMsgId cannot be null!!");
//                                        }
//                                    } if (localTxnTypeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TxnType"));
//                                 
//                                        if (localTxnType != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTxnType));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TxnType cannot be null!!");
//                                        }
//                                    } if (localDePwdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "DePwd"));
//                                 
//                                        if (localDePwd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDePwd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("DePwd cannot be null!!");
//                                        }
//                                    } if (localCallTypeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CallType"));
//                                 
//                                        if (localCallType != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCallType));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CallType cannot be null!!");
//                                        }
//                                    } if (localEntInfoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "EntInfo"));
//                                 
//                                        if (localEntInfo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEntInfo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("EntInfo cannot be null!!");
//                                        }
//                                    } if (localTxnCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TxnCd"));
//                                 
//                                        if (localTxnCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTxnCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TxnCd cannot be null!!");
//                                        }
//                                    } if (localNtnltyCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "NtnltyCd"));
//                                 
//                                        if (localNtnltyCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localNtnltyCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("NtnltyCd cannot be null!!");
//                                        }
//                                    } if (localTypeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Type"));
//                                 
//                                        if (localType != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localType));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Type cannot be null!!");
//                                        }
//                                    } if (localCertTypTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CertTyp"));
//                                 
//                                        if (localCertTyp != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCertTyp));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CertTyp cannot be null!!");
//                                        }
//                                    } if (localCertNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CertNo"));
//                                 
//                                        if (localCertNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCertNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CertNo cannot be null!!");
//                                        }
//                                    } if (localBrkrIssInstrmtCatgyTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "BrkrIssInstrmtCatgy"));
//                                 
//                                        if (localBrkrIssInstrmtCatgy != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBrkrIssInstrmtCatgy));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("BrkrIssInstrmtCatgy cannot be null!!");
//                                        }
//                                    } if (localCntaTelTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CntaTel"));
//                                 
//                                        if (localCntaTel != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCntaTel));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CntaTel cannot be null!!");
//                                        }
//                                    } if (localPayAcctNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "PayAcctNme"));
//                                 
//                                        if (localPayAcctNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPayAcctNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("PayAcctNme cannot be null!!");
//                                        }
//                                    } if (localPhoneTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Phone"));
//                                 
//                                        if (localPhone != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPhone));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Phone cannot be null!!");
//                                        }
//                                    } if (localAreaTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Area"));
//                                 
//                                        if (localArea != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localArea));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Area cannot be null!!");
//                                        }
//                                    } if (localLawyerCertNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "LawyerCertNo"));
//                                 
//                                        if (localLawyerCertNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLawyerCertNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("LawyerCertNo cannot be null!!");
//                                        }
//                                    } if (localLegaCertNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "LegaCertNo"));
//                                 
//                                        if (localLegaCertNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLegaCertNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("LegaCertNo cannot be null!!");
//                                        }
//                                    } if (localIssBrchNoTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "IssBrchNo"));
//                                 
//                                        if (localIssBrchNo != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIssBrchNo));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("IssBrchNo cannot be null!!");
//                                        }
//                                    } if (localProjExApAuthTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ProjExApAuth"));
//                                 
//                                        if (localProjExApAuth != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProjExApAuth));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ProjExApAuth cannot be null!!");
//                                        }
//                                    } if (localQryAcctNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "QryAcctNme"));
//                                 
//                                        if (localQryAcctNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localQryAcctNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("QryAcctNme cannot be null!!");
//                                        }
//                                    } if (localTcktBkNmeTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "TcktBkNme"));
//                                 
//                                        if (localTcktBkNme != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTcktBkNme));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("TcktBkNme cannot be null!!");
//                                        }
//                                    } if (localApplAmtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ApplAmt"));
//                                 
//                                        if (localApplAmt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localApplAmt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ApplAmt cannot be null!!");
//                                        }
//                                    } if (localBkngAmtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "BkngAmt"));
//                                 
//                                        if (localBkngAmt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localBkngAmt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("BkngAmt cannot be null!!");
//                                        }
//                                    } if (localChagAmtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "ChagAmt"));
//                                 
//                                        if (localChagAmt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localChagAmt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("ChagAmt cannot be null!!");
//                                        }
//                                    } if (localCshAmtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CshAmt"));
//                                 
//                                        if (localCshAmt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCshAmt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CshAmt cannot be null!!");
//                                        }
//                                    } if (localCshStAmtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CshStAmt"));
//                                 
//                                        if (localCshStAmt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCshStAmt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CshStAmt cannot be null!!");
//                                        }
//                                    } if (localDateTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Date"));
//                                 
//                                        if (localDate != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDate));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Date cannot be null!!");
//                                        }
//                                    } if (localIntAmtTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "IntAmt"));
//                                 
//                                        if (localIntAmt != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIntAmt));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("IntAmt cannot be null!!");
//                                        }
//                                    } if (localTermTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "Term"));
//                                 
//                                        if (localTerm != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTerm));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("Term cannot be null!!");
//                                        }
//                                    } if (localCrSecTrkTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CrSecTrk"));
//                                 
//                                        if (localCrSecTrk != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCrSecTrk));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CrSecTrk cannot be null!!");
//                                        }
//                                    } if (localCrThdTrkTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "CrThdTrk"));
//                                 
//                                        if (localCrThdTrk != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCrThdTrk));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("CrThdTrk cannot be null!!");
//                                        }
//                                    } if (localDevCdTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "DevCd"));
//                                 
//                                        if (localDevCd != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localDevCd));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("DevCd cannot be null!!");
//                                        }
//                                    } if (localMsgFlgTracker){
//                                      elementList.add(new javax.xml.namespace.QName("",
//                                                                      "MsgFlg"));
//                                 
//                                        if (localMsgFlg != null){
//                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMsgFlg));
//                                        } else {
//                                           throw new org.apache.axis2.databinding.ADBException("MsgFlg cannot be null!!");
//                                        }
//                                    }
//
//                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
//            
//            
//
//        }
//
//  
//
//     /**
//      *  Factory class that keeps the parse method
//      */
//    public static class Factory{
//
//        
//        
//
//        /**
//        * static method to create the object
//        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
//        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
//        * Postcondition: If this object is an element, the reader is positioned at its end element
//        *                If this object is a complex type, the reader is positioned at the end element of its outer element
//        */
//        public static RequestBody parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
//            RequestBody object =
//                new RequestBody();
//
//            int event;
//            java.lang.String nillableValue = null;
//            java.lang.String prefix ="";
//            java.lang.String namespaceuri ="";
//            try {
//                
//                while (!reader.isStartElement() && !reader.isEndElement())
//                    reader.next();
//
//                
//                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
//                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
//                        "type");
//                  if (fullTypeName!=null){
//                    java.lang.String nsPrefix = null;
//                    if (fullTypeName.indexOf(":") > -1){
//                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
//                    }
//                    nsPrefix = nsPrefix==null?"":nsPrefix;
//
//                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
//                    
//                            if (!"RequestBody".equals(type)){
//                                //find namespace for the prefix
//                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
//                                return (RequestBody)ExtensionMapper.getTypeObject(
//                                     nsUri,type,reader);
//                              }
//                        
//
//                  }
//                
//
//                }
//
//                
//
//                
//                // Note all attributes that were handled. Used to differ normal attributes
//                // from anyAttributes.
//                java.util.Vector handledAttributes = new java.util.Vector();
//                
//
//                
//                    
//                    reader.next();
//                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Channel").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Channel" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setChannel(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","SysCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"SysCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setSysCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TranType").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TranType" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTranType(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","PrdCode").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"PrdCode" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setPrdCode(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Flag").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Flag" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setFlag(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Attch").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Attch" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAttch(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Memo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Memo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setMemo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","DecAttch").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"DecAttch" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setDecAttch(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Rmrk").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Rmrk" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRmrk(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","IssRmrk").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"IssRmrk" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setIssRmrk(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","QryTyp").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"QryTyp" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setQryTyp(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","PayMod").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"PayMod" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setPayMod(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RcvBrch").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RcvBrch" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRcvBrch(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CshChnl").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CshChnl" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCshChnl(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","SbjtNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"SbjtNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setSbjtNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","PayFee").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"PayFee" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setPayFee(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","AcctNo1").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"AcctNo1" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAcctNo1(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","BizCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"BizCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setBizCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CrdPwd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CrdPwd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCrdPwd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","DrwTyp").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"DrwTyp" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setDrwTyp(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","VchMod").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"VchMod" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setVchMod(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","BillNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"BillNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setBillNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TranLvl").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TranLvl" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTranLvl(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","BizKind").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"BizKind" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setBizKind(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","BizMod").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"BizMod" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setBizMod(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","MsgIdNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"MsgIdNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setMsgIdNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","DeAcctTyp").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"DeAcctTyp" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setDeAcctTyp(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CltAcct").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CltAcct" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCltAcct(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ChagAcctNmeFif").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ChagAcctNmeFif" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setChagAcctNmeFif(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CustMsg").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CustMsg" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCustMsg(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RcvAcctNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RcvAcctNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRcvAcctNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RcvAcctNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RcvAcctNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRcvAcctNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RcvBkNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RcvBkNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRcvBkNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RcvBkNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RcvBkNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRcvBkNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RcvAcctTyp").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RcvAcctTyp" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRcvAcctTyp(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","OthVchTyp").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"OthVchTyp" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setOthVchTyp(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","VchNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"VchNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setVchNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","IssBrch").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"IssBrch" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setIssBrch(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","AcctBrchNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"AcctBrchNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAcctBrchNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RevcFlg").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"RevcFlg" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setRevcFlg(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","AcctSeqNo1").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"AcctSeqNo1" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAcctSeqNo1(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","AnAmt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"AnAmt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAnAmt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","AnBrch").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"AnBrch" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAnBrch(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","AnBrchNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"AnBrchNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAnBrchNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","AnInAcctNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"AnInAcctNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAnInAcctNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CrAcctNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CrAcctNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCrAcctNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","AcctType").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"AcctType" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAcctType(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Ccy").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Ccy" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCcy(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Amt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Amt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setAmt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","IntFlg").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"IntFlg" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setIntFlg(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","FlowCode").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"FlowCode" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setFlowCode(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","QryMsgId").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"QryMsgId" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setQryMsgId(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TxnType").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TxnType" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTxnType(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","DePwd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"DePwd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setDePwd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CallType").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CallType" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCallType(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","EntInfo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"EntInfo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setEntInfo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TxnCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TxnCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTxnCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","NtnltyCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"NtnltyCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setNtnltyCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Type").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Type" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setType(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CertTyp").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CertTyp" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCertTyp(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CertNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CertNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCertNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","BrkrIssInstrmtCatgy").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"BrkrIssInstrmtCatgy" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setBrkrIssInstrmtCatgy(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CntaTel").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CntaTel" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCntaTel(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","PayAcctNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"PayAcctNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setPayAcctNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Phone").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Phone" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setPhone(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Area").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Area" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setArea(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","LawyerCertNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"LawyerCertNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setLawyerCertNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","LegaCertNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"LegaCertNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setLegaCertNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","IssBrchNo").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"IssBrchNo" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setIssBrchNo(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ProjExApAuth").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ProjExApAuth" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setProjExApAuth(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","QryAcctNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"QryAcctNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setQryAcctNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","TcktBkNme").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"TcktBkNme" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTcktBkNme(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ApplAmt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ApplAmt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setApplAmt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","BkngAmt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"BkngAmt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setBkngAmt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","ChagAmt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"ChagAmt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setChagAmt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CshAmt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CshAmt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCshAmt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CshStAmt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CshStAmt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCshStAmt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Date").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Date" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setDate(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","IntAmt").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"IntAmt" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setIntAmt(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","Term").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"Term" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setTerm(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CrSecTrk").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CrSecTrk" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCrSecTrk(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","CrThdTrk").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"CrThdTrk" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setCrThdTrk(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","DevCd").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"DevCd" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setDevCd(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","MsgFlg").equals(reader.getName())){
//                                
//                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"MsgFlg" +"  cannot be null");
//                                    }
//                                    
//
//                                    java.lang.String content = reader.getElementText();
//                                    
//                                              object.setMsgFlg(
//                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
//                                              
//                                        reader.next();
//                                    
//                              }  // End of if for expected property start element
//                                
//                                    else {
//                                        
//                                    }
//                                  
//                            while (!reader.isStartElement() && !reader.isEndElement())
//                                reader.next();
//                            
//                                if (reader.isStartElement())
//                                // A start element we are not expecting indicates a trailing invalid property
//                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                            
//
//
//
//            } catch (javax.xml.stream.XMLStreamException e) {
//                throw new java.lang.Exception(e);
//            }
//
//            return object;
//        }
//
//        }//end of factory class
//
//        
//
//        }
//           
//    
//        public static class S003001990MS5702
//        implements org.apache.axis2.databinding.ADBBean{
//        
//                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
//                "http://www.adtec.com.cn",
//                "S003001990MS5702",
//                "ns1");
//
//            
//
//                        /**
//                        * field for RequestHeader
//                        */
//
//                        
//                                    protected RequestHeader localRequestHeader ;
//                                
//
//                           /**
//                           * Auto generated getter method
//                           * @return RequestHeader
//                           */
//                           public  RequestHeader getRequestHeader(){
//                               return localRequestHeader;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RequestHeader
//                               */
//                               public void setRequestHeader(RequestHeader param){
//                            
//                                            this.localRequestHeader=param;
//                                    
//
//                               }
//                            
//
//                        /**
//                        * field for RequestBody
//                        */
//
//                        
//                                    protected RequestBody localRequestBody ;
//                                
//
//                           /**
//                           * Auto generated getter method
//                           * @return RequestBody
//                           */
//                           public  RequestBody getRequestBody(){
//                               return localRequestBody;
//                           }
//
//                           
//                        
//                            /**
//                               * Auto generated setter method
//                               * @param param RequestBody
//                               */
//                               public void setRequestBody(RequestBody param){
//                            
//                                            this.localRequestBody=param;
//                                    
//
//                               }
//                            
//
//     
//     
//        /**
//        *
//        * @param parentQName
//        * @param factory
//        * @return org.apache.axiom.om.OMElement
//        */
//       public org.apache.axiom.om.OMElement getOMElement (
//               final javax.xml.namespace.QName parentQName,
//               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{
//
//
//        
//               org.apache.axiom.om.OMDataSource dataSource =
//                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME);
//               return factory.createOMElement(dataSource,MY_QNAME);
//            
//        }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter)
//                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//                           serialize(parentQName,xmlWriter,false);
//         }
//
//         public void serialize(final javax.xml.namespace.QName parentQName,
//                               javax.xml.stream.XMLStreamWriter xmlWriter,
//                               boolean serializeType)
//            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
//            
//                
//
//
//                java.lang.String prefix = null;
//                java.lang.String namespace = null;
//                
//
//                    prefix = parentQName.getPrefix();
//                    namespace = parentQName.getNamespaceURI();
//                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
//                
//                  if (serializeType){
//               
//
//                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.adtec.com.cn");
//                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           namespacePrefix+":S003001990MS5702",
//                           xmlWriter);
//                   } else {
//                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
//                           "S003001990MS5702",
//                           xmlWriter);
//                   }
//
//               
//                   }
//               
//                                    if (localRequestHeader==null){
//
//                                        writeStartElement(null, "", "RequestHeader", xmlWriter);
//
//                                       // write the nil attribute
//                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
//                                      xmlWriter.writeEndElement();
//                                    }else{
//                                     localRequestHeader.serialize(new javax.xml.namespace.QName("","RequestHeader"),
//                                        xmlWriter);
//                                    }
//                                
//                                    if (localRequestBody==null){
//
//                                        writeStartElement(null, "", "RequestBody", xmlWriter);
//
//                                       // write the nil attribute
//                                      writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","nil","1",xmlWriter);
//                                      xmlWriter.writeEndElement();
//                                    }else{
//                                     localRequestBody.serialize(new javax.xml.namespace.QName("","RequestBody"),
//                                        xmlWriter);
//                                    }
//                                
//                    xmlWriter.writeEndElement();
//               
//
//        }
//
//        private static java.lang.String generatePrefix(java.lang.String namespace) {
//            if(namespace.equals("http://www.adtec.com.cn")){
//                return "ns1";
//            }
//            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//        }
//
//        /**
//         * Utility method to write an element start tag.
//         */
//        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
//                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
//            if (writerPrefix != null) {
//                xmlWriter.writeStartElement(namespace, localPart);
//            } else {
//                if (namespace.length() == 0) {
//                    prefix = "";
//                } else if (prefix == null) {
//                    prefix = generatePrefix(namespace);
//                }
//
//                xmlWriter.writeStartElement(prefix, localPart, namespace);
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//        }
//        
//        /**
//         * Util method to write an attribute with the ns prefix
//         */
//        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (xmlWriter.getPrefix(namespace) == null) {
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            xmlWriter.writeAttribute(namespace,attName,attValue);
//        }
//
//        /**
//         * Util method to write an attribute without the ns prefix
//         */
//        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
//                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
//            if (namespace.equals("")) {
//                xmlWriter.writeAttribute(attName,attValue);
//            } else {
//                registerPrefix(xmlWriter, namespace);
//                xmlWriter.writeAttribute(namespace,attName,attValue);
//            }
//        }
//
//
//           /**
//             * Util method to write an attribute without the ns prefix
//             */
//            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
//                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//                java.lang.String attributeNamespace = qname.getNamespaceURI();
//                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
//                if (attributePrefix == null) {
//                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
//                }
//                java.lang.String attributeValue;
//                if (attributePrefix.trim().length() > 0) {
//                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
//                } else {
//                    attributeValue = qname.getLocalPart();
//                }
//
//                if (namespace.equals("")) {
//                    xmlWriter.writeAttribute(attName, attributeValue);
//                } else {
//                    registerPrefix(xmlWriter, namespace);
//                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
//                }
//            }
//        /**
//         *  method to handle Qnames
//         */
//
//        private void writeQName(javax.xml.namespace.QName qname,
//                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//            java.lang.String namespaceURI = qname.getNamespaceURI();
//            if (namespaceURI != null) {
//                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
//                if (prefix == null) {
//                    prefix = generatePrefix(namespaceURI);
//                    xmlWriter.writeNamespace(prefix, namespaceURI);
//                    xmlWriter.setPrefix(prefix,namespaceURI);
//                }
//
//                if (prefix.trim().length() > 0){
//                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                } else {
//                    // i.e this is the default namespace
//                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//                }
//
//            } else {
//                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
//            }
//        }
//
//        private void writeQNames(javax.xml.namespace.QName[] qnames,
//                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
//
//            if (qnames != null) {
//                // we have to store this data until last moment since it is not possible to write any
//                // namespace data after writing the charactor data
//                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
//                java.lang.String namespaceURI = null;
//                java.lang.String prefix = null;
//
//                for (int i = 0; i < qnames.length; i++) {
//                    if (i > 0) {
//                        stringToWrite.append(" ");
//                    }
//                    namespaceURI = qnames[i].getNamespaceURI();
//                    if (namespaceURI != null) {
//                        prefix = xmlWriter.getPrefix(namespaceURI);
//                        if ((prefix == null) || (prefix.length() == 0)) {
//                            prefix = generatePrefix(namespaceURI);
//                            xmlWriter.writeNamespace(prefix, namespaceURI);
//                            xmlWriter.setPrefix(prefix,namespaceURI);
//                        }
//
//                        if (prefix.trim().length() > 0){
//                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        } else {
//                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                        }
//                    } else {
//                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
//                    }
//                }
//                xmlWriter.writeCharacters(stringToWrite.toString());
//            }
//
//        }
//
//
//        /**
//         * Register a namespace prefix
//         */
//        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
//            java.lang.String prefix = xmlWriter.getPrefix(namespace);
//            if (prefix == null) {
//                prefix = generatePrefix(namespace);
//                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
//                while (true) {
//                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
//                    if (uri == null || uri.length() == 0) {
//                        break;
//                    }
//                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
//                }
//                xmlWriter.writeNamespace(prefix, namespace);
//                xmlWriter.setPrefix(prefix, namespace);
//            }
//            return prefix;
//        }
//
//
//  
//        /**
//        * databinding method to get an XML representation of this object
//        *
//        */
//        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
//                    throws org.apache.axis2.databinding.ADBException{
//
//
//        
//                 java.util.ArrayList elementList = new java.util.ArrayList();
//                 java.util.ArrayList attribList = new java.util.ArrayList();
//
//                
//                            elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RequestHeader"));
//                            
//                            
//                                    elementList.add(localRequestHeader==null?null:
//                                    localRequestHeader);
//                                
//                            elementList.add(new javax.xml.namespace.QName("",
//                                                                      "RequestBody"));
//                            
//                            
//                                    elementList.add(localRequestBody==null?null:
//                                    localRequestBody);
//                                
//
//                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
//            
//            
//
//        }
//
//  
//
//     /**
//      *  Factory class that keeps the parse method
//      */
//    public static class Factory{
//
//        
//        
//
//        /**
//        * static method to create the object
//        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
//        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
//        * Postcondition: If this object is an element, the reader is positioned at its end element
//        *                If this object is a complex type, the reader is positioned at the end element of its outer element
//        */
//        public static S003001990MS5702 parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
//            S003001990MS5702 object =
//                new S003001990MS5702();
//
//            int event;
//            java.lang.String nillableValue = null;
//            java.lang.String prefix ="";
//            java.lang.String namespaceuri ="";
//            try {
//                
//                while (!reader.isStartElement() && !reader.isEndElement())
//                    reader.next();
//
//                
//                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
//                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
//                        "type");
//                  if (fullTypeName!=null){
//                    java.lang.String nsPrefix = null;
//                    if (fullTypeName.indexOf(":") > -1){
//                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
//                    }
//                    nsPrefix = nsPrefix==null?"":nsPrefix;
//
//                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
//                    
//                            if (!"S003001990MS5702".equals(type)){
//                                //find namespace for the prefix
//                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
//                                return (S003001990MS5702)ExtensionMapper.getTypeObject(
//                                     nsUri,type,reader);
//                              }
//                        
//
//                  }
//                
//
//                }
//
//                
//
//                
//                // Note all attributes that were handled. Used to differ normal attributes
//                // from anyAttributes.
//                java.util.Vector handledAttributes = new java.util.Vector();
//                
//
//                
//                    
//                    reader.next();
//                
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RequestHeader").equals(reader.getName())){
//                                
//                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                          object.setRequestHeader(null);
//                                          reader.next();
//                                            
//                                            reader.next();
//                                          
//                                      }else{
//                                    
//                                                object.setRequestHeader(RequestHeader.Factory.parse(reader));
//                                              
//                                        reader.next();
//                                    }
//                              }  // End of if for expected property start element
//                                
//                                else{
//                                    // A start element we are not expecting indicates an invalid parameter was passed
//                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                                }
//                            
//                                    
//                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
//                                
//                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","RequestBody").equals(reader.getName())){
//                                
//                                      nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
//                                      if ("true".equals(nillableValue) || "1".equals(nillableValue)){
//                                          object.setRequestBody(null);
//                                          reader.next();
//                                            
//                                            reader.next();
//                                          
//                                      }else{
//                                    
//                                                object.setRequestBody(RequestBody.Factory.parse(reader));
//                                              
//                                        reader.next();
//                                    }
//                              }  // End of if for expected property start element
//                                
//                                else{
//                                    // A start element we are not expecting indicates an invalid parameter was passed
//                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                                }
//                              
//                            while (!reader.isStartElement() && !reader.isEndElement())
//                                reader.next();
//                            
//                                if (reader.isStartElement())
//                                // A start element we are not expecting indicates a trailing invalid property
//                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
//                            
//
//
//
//            } catch (javax.xml.stream.XMLStreamException e) {
//                throw new java.lang.Exception(e);
//            }
//
//            return object;
//        }
//
//        }//end of factory class
//
//        
//
//        }
//           
//    
//            private  org.apache.axiom.om.OMElement  toOM(S003001990MS5702ServiceStub.S003001990MS5702 param, boolean optimizeContent)
//            throws org.apache.axis2.AxisFault {
//
//            
//                        try{
//                             return param.getOMElement(S003001990MS5702ServiceStub.S003001990MS5702.MY_QNAME,
//                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
//                        } catch(org.apache.axis2.databinding.ADBException e){
//                            throw org.apache.axis2.AxisFault.makeFault(e);
//                        }
//                    
//
//            }
//        
//            private  org.apache.axiom.om.OMElement  toOM(S003001990MS5702ServiceStub.S003001990MS5702Response param, boolean optimizeContent)
//            throws org.apache.axis2.AxisFault {
//
//            
//                        try{
//                             return param.getOMElement(S003001990MS5702ServiceStub.S003001990MS5702Response.MY_QNAME,
//                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
//                        } catch(org.apache.axis2.databinding.ADBException e){
//                            throw org.apache.axis2.AxisFault.makeFault(e);
//                        }
//                    
//
//            }
//        
//                                    
//                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, S003001990MS5702ServiceStub.S003001990MS5702 param, boolean optimizeContent, javax.xml.namespace.QName methodQName)
//                                        throws org.apache.axis2.AxisFault{
//
//                                             
//                                                    try{
//
//                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
//                                                            emptyEnvelope.getBody().addChild(param.getOMElement(S003001990MS5702ServiceStub.S003001990MS5702.MY_QNAME,factory));
//                                                            return emptyEnvelope;
//                                                        } catch(org.apache.axis2.databinding.ADBException e){
//                                                            throw org.apache.axis2.AxisFault.makeFault(e);
//                                                        }
//                                                
//
//                                        }
//                                
//                             
//                             /* methods to provide back word compatibility */
//
//                             
//
//
//        /**
//        *  get the default envelope
//        */
//        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
//        return factory.getDefaultEnvelope();
//        }
//
//
//        private  java.lang.Object fromOM(
//        org.apache.axiom.om.OMElement param,
//        java.lang.Class type,
//        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{
//
//        try {
//        
//                if (S003001990MS5702ServiceStub.S003001990MS5702.class.equals(type)){
//                
//                           return S003001990MS5702ServiceStub.S003001990MS5702.Factory.parse(param.getXMLStreamReaderWithoutCaching());
//                    
//
//                }
//           
//                if (S003001990MS5702ServiceStub.S003001990MS5702Response.class.equals(type)){
//                
//                           return S003001990MS5702ServiceStub.S003001990MS5702Response.Factory.parse(param.getXMLStreamReaderWithoutCaching());
//                    
//
//                }
//           
//        } catch (java.lang.Exception e) {
//        throw org.apache.axis2.AxisFault.makeFault(e);
//        }
//           return null;
//        }
//
//
//
//    
//   }
//   