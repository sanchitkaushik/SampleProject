package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.RatingException;
import com.example.demo.model.Movie;
import com.example.demo.repository.MovieDao;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	MovieDao movieDao;

	private static Logger log = LoggerFactory.getLogger(MovieController.class);

	@PostMapping("/create")
	public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
		try {
			if (movie.getRating() > 5) {
				throw new RatingException("Rating should not be greater than 5");
			}
			Movie movie1 = movieDao.save(movie);
			return new ResponseEntity<Movie>(movie1, HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Error" + ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Movie> getMovie(@PathVariable("id") long id) {
		try {
			Optional<Movie> movie1 = movieDao.findById(id);
			return new ResponseEntity<Movie>(movie1.get(), HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Error" + ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/find")
	public ResponseEntity<List<Movie>> getAllMovie() {
		try {
			List<Movie> movie = new ArrayList<Movie>();
			movieDao.findAll().forEach(movie::add);

			return new ResponseEntity<>(movie, HttpStatus.OK);

		} catch (Exception ex) {
			log.error("Error" + ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<Movie> updateMovie(@RequestBody Movie movieReq) {
		try {

			// Optional<Movie> movie = movieDao.findById(id).get();
			Movie movie = movieDao.findById(movieReq.getId()).get();
			if (null != movieReq.getDescription())
				movie.setDescription(movieReq.getDescription());
			if (null != movieReq.getTitle()) {
				movie.setTitle(movieReq.getTitle());
			}
			movie = movieDao.save(movie);
			return new ResponseEntity<Movie>(movie, HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error" + ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Movie> deleteMovie(@PathVariable("id") long id) {
		try {

			// Optional<Movie> movie = movieDao.findById(id).get();
			movieDao.deleteById(id);

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error" + ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/delete")
	public ResponseEntity<Movie> deleteAllMovie() {
		try {

			// Optional<Movie> movie = movieDao.findById(id).get();
			movieDao.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error" + ex);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
