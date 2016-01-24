package org.simple;

import java.io.Serializable;

import org.simple.SimpleApplication.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple rest-controller to create purchases. To test it send a json structure like:
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
public class PurchaseController {

	@Autowired
	EventService eventService;

	@RequestMapping(value = "/purchase/", method = { RequestMethod.POST })
	public Purchase createPurchase(@RequestBody Purchase purchase) {
		/*
		 * do what you need to do to create a purchase
		 */
		PurchasePayload purchasePayload = new PurchasePayload(purchase);

		/*
		 *  and then, where it's needed, fire an event reflecting what has happened above
		 */
		eventService.fireEvent("purchaseCreated", purchasePayload);
		return purchase;
	}

	/*
	 * some adaption of types, enrichment with further information etc. might lead to the resulting event.
	 */
	public class PurchasePayload implements Serializable {

		private Purchase purchase;
		private String userName;

		public PurchasePayload(Purchase purchase) {
			this.purchase = purchase;
		}

		public String getReferenceNumber() {
			return purchase.getId();
		}

		public String getUserName() {
			return purchase.getSureName();
		}

	}

}
