package org.simple;

import java.io.Serializable;

import org.simple.SimpleApplication.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple rest-controller to create invoices. To test it send a json structure like:
 * 
 * <pre>
 * {
 *   "id":"666",
 *   "sureName":"Kilmister"
 * }
 * </pre>
 * 
 * to http://localhost:8080
 *
 */
@RestController(value = "/")
public class InvoiceController {

	@Autowired
	EventService eventService;

	@RequestMapping(value = "/invoice/", method = { RequestMethod.POST })
	public Invoice createInvoice(@RequestBody Invoice invoice) {
		/*
		 * do what you need to do to create an invoice and then ...
		 */
		InvoicePayload invoicePayload = new InvoicePayload(invoice);

		/*
		 * where it's needed, fire an event reflecting what has happened above
		 */
		eventService.fireEvent("invoiceCreated", invoicePayload);
		return invoice;
	}

	/*
	 * some adaption of types, enrichment with further information etc. might lead to the resulting event.
	 */
	public class InvoicePayload implements Serializable {

		private Invoice invoice;
		private String userName;

		public InvoicePayload(Invoice invoice) {
			this.invoice = invoice;
		}

		public String getReferenceNumber() {
			return invoice.getId();
		}

		public String getUserName() {
			return invoice.getSureName();
		}

	}

}
