package com.in28minutes.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.in28minutes.microservices.currencyconversionservice.model.CurrencyConversionBean;
import com.in28minutes.microservices.currencyconversionservice.service.CurrencyExchangeServiceProxy;

@RestController
public class CurrencyConversionController {

	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping(path = "/currency-exchange-feign/from/{from}/to/{to}/quantity/{quantity}")
	// http://localhost:8100/currency-exchange-feign/from/USD/to/INR/quantity/1000   show only one request in logs
	//http://localhost:8765/{app-name}/{uri}  show two requests in logs through zuul api
	// http://localhost:8765/currency-conversion-service/currency-exchange-feign/from/USD/to/INR/quantity/1000
	public CurrencyConversionBean convertCurrencyWithFeignClient(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
	}

	@GetMapping(path = "/currency-exchange/from/{from}/to/{to}/quantity/{quantity}")
	// http://localhost:8100/currency-exchange/from/USD/to/INR/quantity/1000
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {
		// Feign Problem===============
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);

		CurrencyConversionBean response = responseEntity.getBody();

		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());
		// return new
		// CurrencyConversionBean(1L,from,to,BigDecimal.ONE,quantity,quantity,0);
	}

}
