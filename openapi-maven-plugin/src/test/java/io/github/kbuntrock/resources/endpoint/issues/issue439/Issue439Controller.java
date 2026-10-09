package io.github.kbuntrock.resources.endpoint.issues.issue439;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Reproduces issue #439 : a void-returning endpoint that writes a file directly to the
 * {@link HttpServletResponse} is a common Spring MVC file-download pattern, but was documented
 * as an empty response because the return type is {@code void}.
 */
@RestController
@RequestMapping("/issue439")
public class Issue439Controller {

	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void download(final HttpServletResponse response) throws IOException {
		response.getOutputStream().write("hello".getBytes());
	}

	// A void endpoint with no HttpServletResponse parameter is not a file-download pattern :
	// it must keep documenting an empty response, since there is nowhere to write a body to.
	@GetMapping(value = "/no-op")
	public void noOp() {
		// Intentionally empty.
	}

	// A void endpoint which only produces JSON is not a binary file download either, so it
	// must also keep documenting an empty response.
	@GetMapping(value = "/json-void", produces = MediaType.APPLICATION_JSON_VALUE)
	public void jsonVoid(final HttpServletResponse response) {
		// Intentionally empty.
	}
}
