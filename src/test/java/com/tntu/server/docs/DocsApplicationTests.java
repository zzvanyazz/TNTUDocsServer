package com.tntu.server.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.RunnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DocsApplicationTests {

	private static final String authHeader = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjE0NjIxOTI5LCJpc3MiOiJ0bnR1LWRvY3MiLCJpYXQiOjE2MTM0MTIzMjl9.dl1Plw5KG0VPZjEBQGI0mBVA7_7-PZghSsYTwYFbAPhwIa5QLh6fIcDGkM5ZmS4Yw4dRSIMNkfGVwqV53EwR4g";


    @Autowired
	public MockMvc mockMvc;


	@Test
	public void getResourcesTree() throws Exception {
        var runnables = new Runnable[100];
        for (int i = 0; i < 100; i++)
            runnables[i] = this::run;
		var t1 = System.currentTimeMillis();
        Stream.of(runnables).parallel().forEach(Runnable::run);
        var t2 = System.currentTimeMillis();
        System.out.println(t2-t1);
	}

	public void run() {
        try {
            mockMvc.perform(get("/v1/docs/public/tree")
                    .header("Authorization", authHeader))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
