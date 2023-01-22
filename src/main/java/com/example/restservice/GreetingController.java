package com.example.restservice;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.MediaType;


import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
public class GreetingController {

	private Coordinator c;

	public GreetingController() {
		c = new Coordinator();
	}

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/review/{id}")
	public Map<String, Object> getReview(@PathVariable(required = false) long id) throws Exception { 
		Review review = c.getReview(id);
		HashMap<String, Object> hm = new HashMap(); 
		hm.put("location", review.getLocation());
		hm.put("toiletRating", review.getToiletRating());
		hm.put("sinkRating", review.getSinkRating());
		hm.put("noiseRating", review.getNoiseRating());
		hm.put("comment", review.getComment());

		return hm;
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value = "/reviewcreate", consumes=MediaType.APPLICATION_JSON_VALUE)
	public void createReview(@RequestBody Map<String, Object> payload) throws Exception { 
		c.addReview(payload.get("location").toString(), Integer.valueOf(payload.get("toiletRating").toString()), Integer.valueOf(payload.get("sinkRating").toString()), Integer.valueOf(payload.get("noiseRating").toString()), payload.get("comment").toString() );
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/reviewdeletion/{id}")
	public boolean deleteReview(@PathVariable(required = false) long id) throws Exception { 
		return c.deleteReview(id);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/reviewsbylocation/{location}")
	public List<Object[]> getReviewsByLocation(@PathVariable(required = false) String location) throws Exception { 
		return c.getReviews(location);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/ratingatlocation/{location}")
	public float getRatingAtLocation(@PathVariable(required = false) String location) throws Exception { 
		return c.getAverageRating(location);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/commentsbylocation/{location}")
	public String[] getCommentsByLocation(@PathVariable(required = false) String location) throws Exception { 
		return c.getRandomComments(location);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/latestcomments")
	public String[] getLatestComments() throws Exception { 
		return c.getLatestComments();
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/tablecreation")
	public void createTableIfMissing() {
		c.createReviewTableIfMissing();
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/tabledeletion")
	public void deleteTableIfMissing() {
		c.deleteReviewTable();
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/allreviews")
	public List<Object[]> getReviews() throws Exception {
		return c.getReviews();
	}


}
