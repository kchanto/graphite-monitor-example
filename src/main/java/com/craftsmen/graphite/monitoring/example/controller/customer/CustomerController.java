package com.craftsmen.graphite.monitoring.example.controller.customer;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.craftsmen.graphite.monitoring.example.controller.AbstractHttpController;
import com.craftsmen.graphite.monitoring.example.entities.Customer;
import com.craftsmen.graphite.monitoring.example.service.CustomerService;

@RestController
public class CustomerController extends AbstractHttpController {

	@Inject
	private CustomerService customerService;

	@Inject
	private CustomerToJsonMapper customerToJsonMapper;

	@Inject
	private JsonToCustomerMapper jsonToCustomerMapper;

	@Inject
	private RequestValidator requestValidator;

	/**
	 * This method will retrieve the {@link Customer} for the id provided.
	 *
	 * If the {@link Customer} cannot be found, a {@link NoSuchElementException}
	 * will be thrown and a {@link ResponseEntity} with
	 * {@link HttpStatus.NO_CONTENT} will be returned. If the {@link Customer}
	 * exists, it will be returned in the {@link ResponseEntity}.
	 *
	 * @param id
	 *            the unique identifier for this customer
	 * @return {@link ResponseEntity} containing {@link Customer} retrieved if
	 *         found, the HTTP headers and the HTTP status code
	 */
	@RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> get(@PathVariable Long id, HttpServletRequest request) {
		logApiCalls(request);
		String requestUrl = linkTo(CustomerController.class).withSelfRel().getHref();
		Customer customer = customerService.findById(id);
		String response = customerToJsonMapper.mapCustomerToJson(customer, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}

	/**
	 * This method will retrieve {@link Customer}s by their first name.
	 *
	 * If the {@link Customer} cannot be found, an empty result will be will be
	 * returned in the {@link ResponseEntity}. If one or more {@link Customer}s
	 * are found, they will be returned in the {@link ResponseEntity}.
	 *
	 * @param firstName
	 *            the {@link Customer}'s first name
	 * @return {@link ResponseEntity} containing the {@link Customer}s retrieved
	 *         if found, the HTTP headers and the HTTP status code
	 */
	@RequestMapping(value = "/customers/firstName/{firstName}", method = RequestMethod.GET, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> getByFirstName(@PathVariable String firstName, HttpServletRequest request) {
		logApiCalls(request);
		String requestUrl = linkTo(CustomerController.class).withSelfRel().getHref();
		List<Customer> customers = customerService.findByFirstName(firstName);
		String response = customerToJsonMapper.mapCustomersToJson(customers, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}

	/**
	 * This method will retrieve {@link Customer}s by their last name.
	 *
	 * If the {@link Customer} cannot be found, an empty result will be will be
	 * returned in the {@link ResponseEntity}. If one or more {@link Customer}s
	 * are found, they will be returned in the {@link ResponseEntity}.
	 *
	 * {@link HttpStatus.NO_CONTENT} will be returned. If one or more
	 * {@link Customer}s are found, they will be returned in the
	 * {@link ResponseEntity}.
	 *
	 * @param lastName
	 *            the {@link Customer}'s last name
	 * @return {@link ResponseEntity} containing the {@link Customer}s retrieved
	 *         if found, the HTTP headers and the HTTP status code
	 */
	@RequestMapping(value = "/customers/lastName/{lastName}", method = RequestMethod.GET, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> getByLastName(@PathVariable String lastName, HttpServletRequest request) {
		logApiCalls(request);
		String requestUrl = linkTo(CustomerController.class).withSelfRel().getHref();
		List<Customer> customers = customerService.findByLastName(lastName);
		String response = customerToJsonMapper.mapCustomersToJson(customers, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}

	/**
	 * This method will retrieve {@link Customer}s by their first and last name.
	 *
	 * If the {@link Customer} cannot be found, an empty result will be will be
	 * returned in the {@link ResponseEntity}. If one or more {@link Customer}s
	 * are found, they will be returned in the {@link ResponseEntity}.
	 *
	 * {@link HttpStatus.NO_CONTENT} will be returned. If one or more
	 * {@link Customer}s are found, they will be returned in the
	 * {@link ResponseEntity}.
	 *
	 * @param firstName
	 *            the {@link Customer}'s first name
	 * @param lastName
	 *            the {@link Customer}'s last name
	 * @return {@link ResponseEntity} containing the {@link Customer}s retrieved
	 *         if found, the HTTP headers and the HTTP status code
	 */
	@RequestMapping(value = "/customers/firstName/{firstName}/lastName/{lastName}", method = RequestMethod.GET, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> getByFirstNameAndLastName(@PathVariable String firstName,
			@PathVariable String lastName, HttpServletRequest request) {
		logApiCalls(request);
		String requestUrl = linkTo(CustomerController.class).withSelfRel().getHref();
		List<Customer> customers = customerService.findByFirstNameAndLastName(firstName, lastName);
		String response = customerToJsonMapper.mapCustomersToJson(customers, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}

	/**
	 * This method will retrieve all {@link Customer}s.
	 *
	 * If the {@link Customer} cannot be found, an empty result will be will be
	 * returned in the {@link ResponseEntity}. If one or more {@link Customer}s
	 * are found, they will be returned in the {@link ResponseEntity}.
	 *
	 * {@link HttpStatus.NO_CONTENT} will be returned. If one or more
	 * {@link Customer}s are found, they will be returned in the
	 * {@link ResponseEntity}.
	 *
	 * @return {@link ResponseEntity} containing the {@link Customer}s retrieved
	 *         if found, the HTTP headers and the HTTP status code
	 */
	@RequestMapping(value = "/customers", method = RequestMethod.GET, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> getAll(HttpServletRequest request) {
		logApiCalls(request);
		String requestUrl = linkTo(CustomerController.class).withSelfRel().getHref();
		List<Customer> customers = customerService.findAll();
		String response = customerToJsonMapper.mapCustomersToJson(customers, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}

	/**
	 * This method will delete the {@link Customer} with the id provided.
	 *
	 * @param id
	 *            the unique identifier for this customer
	 * @return {@link ResponseEntity} containing the HTTP headers and the HTTP
	 *         status code
	 */
	@RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> delete(@PathVariable Long id, HttpServletRequest request) {
		logApiCalls(request);
		try {
			Customer customer = customerService.findById(id);
			System.out.println(customer.toString() + " found and will be deleted");
		} catch (NoSuchElementException e) {
			// Nothing to delete.
		}
		customerService.delete(id);
		return new ResponseEntity<String>(createHeaders(request.getMethod()), HttpStatus.NO_CONTENT);
	}

	/**
	 * This method will update the customer with the given id. It is only used
	 * for updating.
	 *
	 * First try to find the {@link Customer} by the id provided. If it can't be
	 * found, a {@link NoSuchElementException} will be thrown and a
	 * {@link ResponseEntity} with {@link HttpStatus.NO_CONTENT} will be
	 * returned. If the {@link Customer} exists, it will be updated and returned
	 * in the {@link ResponseEntity}.
	 *
	 * @param id
	 *            the unique identifier for this customer
	 * @param requestBody
	 *            the request body with the customer details
	 * @return {@link ResponseEntity} containing the updated {@link Customer} if
	 *         found, the HTTP headers and the HTTP status code
	 */
	@RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> put(@PathVariable Long id, @RequestBody final String requestBody,
			HttpServletRequest request) {
		logApiCalls(request);
		requestValidator.validateJson(requestBody);
		Customer customer = customerService.findById(id);
		customer = jsonToCustomerMapper.mapJsonToCustomer(id, requestBody);
		customerService.save(customer);
		String requestUrl = linkTo(CustomerController.class).withSelfRel().getHref();
		String response = customerToJsonMapper.mapCustomerToJson(customer, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(request.getMethod()), HttpStatus.OK);
	}

	/**
	 * This method will create a new {@link Customer}.
	 *
	 * If the {@link Customer} cannot be found, a {@link NoSuchElementException}
	 * will be thrown and a {@link ResponseEntity} with
	 * {@link HttpStatus.NO_CONTENT} will be returned. If the {@link Customer}
	 * exists, it will be returned in the {@link ResponseEntity}.
	 *
	 * @param requestBody
	 *            the request body with the customer details
	 * @return {@link ResponseEntity} containing the new {@link Customer}, the
	 *         HTTP headers and the HTTP status code
	 */
	@RequestMapping(value = "/customer", method = RequestMethod.POST, produces = JSON_MIME_TYPE)
	@ResponseBody
	@Timed
	@ExceptionMetered
	public ResponseEntity<String> post(@RequestBody final String requestBody, HttpServletRequest request) {
		logApiCalls(request);
		requestValidator.validateJson(requestBody);
		Customer customer = jsonToCustomerMapper.mapJsonToCustomer(requestBody);
		customer = customerService.save(customer);
		String requestUrl = request.getRequestURL().toString();
		String response = customerToJsonMapper.mapCustomerToJson(customer, requestUrl);
		return new ResponseEntity<String>(response, createHeaders(requestUrl, customer.getId(), request.getMethod()),
				HttpStatus.CREATED);
	}
}
