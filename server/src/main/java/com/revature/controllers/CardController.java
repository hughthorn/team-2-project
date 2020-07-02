package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Card;
import com.revature.services.CardService;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "*", 
methods = {RequestMethod.GET, RequestMethod.PUT,
			RequestMethod.PATCH, RequestMethod.POST},
allowedHeaders = {"Content-Type"})

public class CardController {

	@Autowired
	CardService cardService;
	
/* START GET METHODS */
	
	//GET a posticket by its card_id and returns it as a JSON object.
	@GetMapping("/employee/tickets/{id}")
	public Card getCardById(@PathVariable int id) {
//		id = 1; //enable this line to replace any received information with hardcoded value
		return cardService.getCardById(id); //path variable is used as an arg for method in cardService
	}
	
	//GET a list of postickets by its user_id and returns it as an array of JSON objects.
	@GetMapping("/employee/history/{id}")
	public List<Card> getCardByUid(@PathVariable int id) {
		System.out.println(id);
		return cardService.getCardsByUser_id(id); //path variable is used as an arg for method in cardService
	}
	
	//GET all postickets and return them as an array of JSON objects.
	@GetMapping("/administrators/all")
	public List<Card> getAllCards() {
			return cardService.getAllCards();
	}
	
	//GET all cards with a "pending" ticket status (ticket_status=1) and returns them as an array of JSON objects.
	@GetMapping("/administrators/recent")
	public List<Card> getPendingCards() {
		return cardService.getCardsByTicketStatus(1);
	}	
	
	//GET all cards with an "accepted" ticket status (ticket_status=2) and returns them as an array of JSON objects.
	@GetMapping("/administrators/accepted")
	public List<Card> getAcceptedCards() {
		return cardService.getCardsByTicketStatus(2);
	}
	
	//GET all cards with a "resolved" ticket status (ticket_status=2) and returns them as an array of JSON objects.
	@GetMapping("/employee/resolved")
	public List<Card> getResolvedCards() {
		return cardService.getCardsByTicketStatus(3);
	}
	
	
	//GET all posts and return them as an array of JSON objects.
	@GetMapping("/employee/tickets")
	public List<Card> getAllPosts() {
			return cardService.getAllCards();
	}
	
	/* !!I'm not sure this endpoint is going to be used anymore!! */
	//GET all cards with a ticket_status whose id# is equal to the one sent in in the body of the request ( "ticket_status": {id} ) and return them as an array of JSON objects.
	@GetMapping("/ticket-status")
	public List<Card> getCardsByTicketStatus(@RequestBody Card card) { //maps the ticket_status received in body to new Card instance
		int ts=card.getTicket_status(); //extracts that value as an int
		return cardService.getCardsByTicketStatus(ts); //uses extracted value as arg for method in cardService
	}
	
	//GET all cards with a ticket_status whose id# is equal to the one received in the path variable
	@GetMapping("/employees/post/{statusId}")
	public List<Card> getCardsByTicketStatus(@PathVariable int statusId) {
		return cardService.getCardsByTicketStatus(statusId); //path variable is used as an arg for method in cardService
	}
/* END GET METHODS */	


/* START PATCH METHOD */
	
	//take in a posticket object, then PATCH the database entry for the posticket with that card_id.
	@PatchMapping("/managers/approvals")
	public Card updateTicket(@RequestBody Card card) {
		return cardService.updateTicket(card); //take received data as new card, then pass it as args to CardService
	}
/* END PATCH METHOD */
	
	
/* START POST METHOD*/
	
	//POST new posticket into the database. expects to receive ticket_status, title, message, user_id. entry_time is automatically generated by the server.
	@PostMapping("/employee/post")
	public Card save(@RequestBody Card card) {
		return cardService.saveNew(card); //grab received data as new card, then use it as an arg for method in cardService
	}
/* END POST METHOD*/

}