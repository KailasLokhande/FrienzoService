package com.kailas.frienzoservice.controllers;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kailas.frienzoservice.model.FileDiffAPIInput;
import com.kailas.frienzoservice.util.diff_match_patch;

@Controller
public class FileDiffController {

	public class FileDiffAPIOutput implements Serializable {
		private String output;

		public String getOutput() {
			return output;
		}

		public void setOutput(String output) {
			this.output = output;
		}
	}

	private static final Logger logger = LoggerFactory
			.getLogger(FileDiffController.class);

	@RequestMapping(value = "/file/diff/test", method = RequestMethod.GET)
	public @ResponseBody FileDiffAPIInput getSample() {
		FileDiffAPIInput input = new FileDiffAPIInput();
		input.setText1("kailas");
		input.setText2("ka");
		return input;
	}

	@RequestMapping(value = "/file/diff", method = RequestMethod.POST)
	public @ResponseBody String diffFile(@RequestBody FileDiffAPIInput input) {
		logger.info("Diffing Between Text 1: \n " + input.getText1()
				+ " \nText 2: \n" + input.getText2());
		diff_match_patch dmp = new diff_match_patch();

		String htmldiff = dmp.diff_prettyHtml(dmp.diff_main(input.getText1(),
				input.getText2()));
		System.out.println(htmldiff);
		return htmldiff;
		// throw new RuntimeException("Fail");
	}

}
