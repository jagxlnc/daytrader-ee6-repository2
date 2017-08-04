/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.ibm.websphere.samples.daytrader.rest.orders;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ibm.websphere.samples.daytrader.OrderDataBean;
import com.ibm.websphere.samples.daytrader.TradeServices;
import com.ibm.websphere.samples.daytrader.direct.TradeDirect;

@Path(value = "/orders")
public class Orders {
	
	
	/* Note: orderStatus (open, processing, completed, closed, cancelled) */


    public Orders() {}
   
    private static TradeDirect impl = new TradeDirect();
 
	/**
     * @see TradeServices#buy(String, String, double)
     */
    @POST
    @Path("/{userID}/{symbol}/{quantity}/{orderProcessingMode}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public OrderDataBean buy(@PathParam(value = "userID") String userID, @PathParam(value = "symbol") String symbol, 
			@PathParam(value = "quantity") double quantity, 
			@PathParam(value = "orderProcessingMode") int orderProcessingMode) throws Exception {
		return impl.buy(userID, symbol, quantity, orderProcessingMode);
	}	
    
	/**
     * @see TradeServices#sell(String, Integer)
     */
    @POST
    @Path("/{userID}/{holdingID}/{orderProcessingMode}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public OrderDataBean sell(@PathParam(value = "userID") String userID, @PathParam(value = "holdingID") Integer holdingID, 
			@PathParam(value = "orderProcessingMode") int orderProcessingMode) throws Exception {
		return impl.sell(userID, holdingID, orderProcessingMode);
	}
    
	/**
     * @see TradeServices#queueOrder(Integer, boolean)
     */
    @PUT
    @Path("/{orderID}/{twoPhase}")
    @Consumes({"application/json"})
	public void queueOrder(@PathParam(value = "orderID") Integer orderID, 
			@PathParam(value = "twoPhase") boolean twoPhase) throws Exception {
		impl.queueOrder(orderID, twoPhase);
	}
    
	/**
     * @see TradeServices#completeOrder(Integer, boolean)
     */
    @POST
    @Path("/{orderID}/{twoPhase}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public OrderDataBean completeOrder(@PathParam(value = "orderID") Integer orderID, 
    		@PathParam(value = "twoPhase") boolean twoPhase) throws Exception {
		return impl.completeOrder(orderID, twoPhase);
	}

	/**
     * @see TradeServices#cancelOrder(Integer, boolean)
     */
    @DELETE
    @Path("/{orderID}/{twoPhase}")
    @Consumes({"application/json"})
	public void cancelOrder(@PathParam(value = "orderID") Integer orderID, 
			@PathParam(value = "twoPhase") boolean twoPhase) throws Exception {
		throw new UnsupportedOperationException( "TradeDirect:cancelOrder method not supported" );
	}

    
	/**
     * @see TradeServices#orderCompleted(String, Integer)
     */
    @PUT
    @Path("/{orderID}/{userID}/{twoPhase}")
    @Consumes({"application/json"})
	public void orderCompleted(@PathParam(value = "userID") String userID, 
			@PathParam(value = "orderID") Integer orderID) throws Exception {
		throw new UnsupportedOperationException( "TradeDirect:orderCompleted method not supported" );
	}

	/**
     * @see TradeServices#getOrders(String)
     */
    @GET
    @Path("/{userID}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
	public Collection<OrderDataBean> getOrders(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getOrders(userID);
	}
    
	/**
     * @see TradeServices#getClosedOrders(String)
     */
    @GET
    @Path("/closed/{userID}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Collection<OrderDataBean> getClosedOrders(@PathParam(value = "userID") String userID) throws Exception {
		return impl.getOrders(userID);
	}

}
