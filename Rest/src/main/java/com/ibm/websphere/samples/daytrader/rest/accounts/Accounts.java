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

package com.ibm.websphere.samples.daytrader.rest.accounts;

import java.math.BigDecimal;
import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ibm.websphere.samples.daytrader.AccountDataBean;
import com.ibm.websphere.samples.daytrader.AccountProfileDataBean;
import com.ibm.websphere.samples.daytrader.HoldingDataBean;
import com.ibm.websphere.samples.daytrader.MarketSummaryDataBean;
import com.ibm.websphere.samples.daytrader.OrderDataBean;
import com.ibm.websphere.samples.daytrader.QuoteDataBean;
import com.ibm.websphere.samples.daytrader.RunStatsDataBean;
import com.ibm.websphere.samples.daytrader.TradeServices;
import com.ibm.websphere.samples.daytrader.direct.TradeDirect;

@Path(value = "/accounts")
public class Accounts {


    public Accounts() {}

//    @GET
//    @Path(value = "/search/{searchstring}")
//    @Produces(value = { "application/json" })
//    public AddressList search(@PathParam(value = "searchstring") String searchString) {
//        AddressList addressList = new AddressList();
//        List<Address> addresses = addressList.getAddresses();
//        Iterator<Address> addressIter = AddressBookDatabase.getAddresses();
//        while (addressIter.hasNext()) {
//            Address address = addressIter.next();
//            if (address.getEntryName().startsWith(searchString)) {
//                addresses.add(address);
//            }
//        }
//        return addressList;
//    }
//
//    @GET
//    @Produces(value = { "application/json" })
//    public AddressList getAddresses() {
//        AddressList addressList = new AddressList();
//        List<Address> addresses = addressList.getAddresses();
//        Iterator<Address> addressIter = AddressBookDatabase.getAddresses();
//        while (addressIter.hasNext()) {
//            Address address = addressIter.next();
//            addresses.add(address);
//        }
//        return addressList;
//    }
//
//    @Path("/{entryName}")
//    public Address getAddress(@PathParam(value = "entryName") String entryName) {
//        Address addr = AddressBookDatabase.getAddress(entryName);
//        return addr;
//    }
   
    private static TradeDirect impl = new TradeDirect();
 
    // TODO: make this a separate micro-service under the /market/summary path
	/**
     * @see TradeServices#getMarketSummary()
     */
    @GET
    @Path("/marketsummary")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public MarketSummaryDataBean getMarketSummary() throws Exception {
		return impl.getMarketSummary();
	}

	/**
     * @see TradeServices#buy(String, String, double)
     */
    @POST
    @Path("/{userID}/holdings")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public OrderDataBean buy(String userID, String symbol, double quantity, int orderProcessingMode) throws Exception {
		return impl.buy(userID, symbol, quantity, orderProcessingMode);
	}
	

	/**
     * @see TradeServices#sell(String, Integer)
     */
    @POST
    @Path("/{userID}/holdings/{holdingID}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public OrderDataBean sell(String userID, Integer holdingID,	int orderProcessingMode) throws Exception {
		return impl.sell(userID, holdingID, orderProcessingMode);
	}

	/**
     * @see TradeServices#queueOrder(Integer, boolean)
     */
	public void queueOrder(Integer orderID, boolean twoPhase) throws Exception {
		throw new UnsupportedOperationException( "TradeDirect:queueOrder method not supported" );
	}

	/**
     * @see TradeServices#completeOrder(Integer, boolean)
     */
	public OrderDataBean completeOrder(Integer orderID, boolean twoPhase) throws Exception {
		throw new UnsupportedOperationException( "TradeDirect:completeOrder method not supported" );
	}


	/**
     * @see TradeServices#cancelOrder(Integer, boolean)
     */
	public void cancelOrder(Integer orderID, boolean twoPhase) throws Exception {
		throw new UnsupportedOperationException( "TradeDirect:cancelOrder method not supported" );
	}

	/**
     * @see TradeServices#orderCompleted(String, Integer)
     */
	public void orderCompleted(String userID, Integer orderID) throws Exception {
		throw new UnsupportedOperationException( "TradeDirect:orderCompleted method not supported" );
	}

	/**
     * @see TradeServices#getOrders(String)
     */
    @GET
    @Path("/{userID}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public Collection<OrderDataBean> getOrders(String userID) throws Exception {
		return impl.getOrders(userID);
	}

	/**
     * @see TradeServices#getClosedOrders(String)
     */
    @GET
    @Path("/{userID}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Collection<OrderDataBean> getClosedOrders(String userID) throws Exception {
		return impl.getOrders(userID);
	}

	/**
     * @see TradeServices#createQuote(String, String, BigDecimal)
     */
	public QuoteDataBean createQuote(String symbol, String companyName, BigDecimal price) throws Exception {
		return impl.createQuote(symbol, companyName, price);
	}

    // TODO: make this a separate micro-service under the /market/quotes/{symbol} path
	/**
     * @see TradeServices#getQuote(String)
     */
    @GET
    @Path("/quotes/{symbol}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public QuoteDataBean getQuote(String symbol) throws Exception {
		return impl.getQuote(symbol);
	}


    // TODO: make this a separate micro-service under the /market/quotes path
	/**
     * @see TradeServices#getAllQuotes(String)
     */
    @GET
    @Path("/quotes")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public Collection<QuoteDataBean> getAllQuotes() throws Exception {
		return impl.getAllQuotes();
	}

    // TODO: make this a separate micro-service under the /market/quotes path
	/**
     * @see TradeServices#updateQuotePriceVolume(String, BigDecimal, double)
     */
    @POST
    @Path("/quotes/{symbol}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public QuoteDataBean updateQuotePriceVolume(String symbol, BigDecimal changeFactor, double sharesTraded) throws Exception {
		return impl.updateQuotePriceVolume(symbol, changeFactor, sharesTraded);
	}
	
	/**
     * @see TradeServices#getHoldings(String)
     */
    @GET
    @Path("/{userID}/holdings")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public Collection<HoldingDataBean> getHoldings(String userID) throws Exception {
		return impl.getHoldings(userID);
	}

	/**
     * @see TradeServices#getHolding(Integer)
     */
    @GET
    @Path("/{userID}/holdings/{holdingID}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public HoldingDataBean getHolding(Integer holdingID) throws Exception {
		return impl.getHolding(holdingID);
	}

	/**
     * @see TradeServices#getAccountData(String)
     */
    @GET
    @Path("/{userID}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public AccountDataBean getAccountData(String userID) throws Exception {
		return impl.getAccountData(userID);
	}

	/**
     * @see TradeServices#getAccountProfileData(String)
     */
    @GET
    @Path("/{userID}/profile")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
    public AccountProfileDataBean getAccountProfileData(String userID) throws Exception {
		return impl.getAccountProfileData(userID);
	}

	/**
     * @see TradeServices#updateAccountProfile(AccountProfileDataBean)
     */
    @PUT
    @Path("/{userID}/profile")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public AccountProfileDataBean updateAccountProfile(AccountProfileDataBean profileData) throws Exception {
		return impl.updateAccountProfile(profileData);
	}

	/**
     * @see TradeServices#login(String, String)
     */
    @PUT
    @Path("/{userID}/{password}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public AccountDataBean login(@PathParam(value = "userID") String userID, 
			@PathParam(value = "password") String password) throws Exception {
		return impl.login(userID, password);
	}

	/**
     * @see TradeServices#logout(String)
     */
    @PUT
    @Path("/{userID}")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public void logout(@PathParam(value = "userID") String userID) throws Exception {
		impl.logout(userID);
	}

	/**
     * @see TradeServices#register(String, String, String, String, String,
     *      String, BigDecimal, boolean)
     */
    @POST
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public AccountDataBean register(String userID, String password, String fullname, 
			String address, String email, String creditcard, BigDecimal openBalance) throws Exception {
		return impl.register(userID, password, fullname, address, email, creditcard, openBalance);
	}

	/**
     * @see TradeServices#resetTrade(boolean)
     */
    @DELETE
    @Path("/runstats")
    @Produces({"application/xml", "application/json"})
    @Consumes({"application/xml", "application/json"})
	public RunStatsDataBean resetTrade(boolean deleteAll) throws Exception {
		return impl.resetTrade(deleteAll);
	}

}
