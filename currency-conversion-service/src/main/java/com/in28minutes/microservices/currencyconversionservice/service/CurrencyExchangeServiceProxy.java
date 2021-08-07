package com.in28minutes.microservices.currencyconversionservice.service;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.in28minutes.microservices.currencyconversionservice.model.CurrencyConversionBean;


// here hard coded the url localhost:8000 so load balancing not happening
//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")

// hence add @RibbonClient and remove url hardcoding
// connecting to currency exchange service
//@FeignClient(name = "currency-exchange-service")
// comment above line connect to zual api gateway instead of exchange service
@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

//	@GetMapping(path = "/currency-exchange/from/{from}/to/{to}")
//	//http://localhost:8000/currency-exchange/from/USD/to/INR
	@GetMapping(path = "currency-exchange-service/currency-exchange/from/{from}/to/{to}")
	//http://localhost:8765/currency-exchange-service/currency-exchange/from/EUR/to/INR
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable String from, @PathVariable String to);

}
