/*******************************************************************************
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Titouan COMPIEGNE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *******************************************************************************/
package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import models.Todo;
import play.Play;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;
import play.libs.ws.WSResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * 
 * @author Titouan COMPIEGNE
 *
 */
public class TodoService {

	private static final String OAUTH_RESOURCE_SERVER_URL = Play.application().configuration().getString("oauth.resource.server.url");

	public static List<Todo> findAll(String accessToken) {
		WSRequestHolder req = WS.url(OAUTH_RESOURCE_SERVER_URL + "/rest/todos");
		req.setHeader("Authorization", "Bearer " + accessToken);

		Promise<List<Todo>> jsonPromise = req.get().map(new Function<WSResponse, List<Todo>>() {
			public List<Todo> apply(WSResponse response) {
				JsonNode json = response.asJson();
				ArrayNode results = (ArrayNode) json;
				List<Todo> todos = new ArrayList<Todo>();
				Iterator<JsonNode> it = results.iterator();

				while (it.hasNext()) {
					JsonNode node = it.next();
					Todo todo = new Todo();
					todo.setDescription(node.get("description").asText());
					todos.add(todo);
				}

				return todos;
			}
		});
		return jsonPromise.get(5000);
	}

	public static List<Todo> findByUsername(String accessToken, String username) {
		WSRequestHolder req = WS.url(OAUTH_RESOURCE_SERVER_URL + "/rest/todos/" + username);
		req.setHeader("Authorization", "Bearer " + accessToken);

		Promise<List<Todo>> jsonPromise = req.get().map(new Function<WSResponse, List<Todo>>() {
			public List<Todo> apply(WSResponse response) {
				JsonNode json = response.asJson();
				ArrayNode results = (ArrayNode) json;
				List<Todo> todos = new ArrayList<Todo>();
				Iterator<JsonNode> it = results.iterator();

				while (it.hasNext()) {
					JsonNode node = it.next();
					Todo todo = new Todo();
					todo.setId(node.get("id").asLong());
					todo.setDescription(node.get("description").asText());
					todo.setUsername(node.get("username").asText());
					todos.add(todo);
				}

				return todos;
			}
		});
		return jsonPromise.get(5000);
	}

	public static Todo add(String accessToken, String username, String description) {
		WSRequestHolder req = WS.url(OAUTH_RESOURCE_SERVER_URL + "/rest/todos/add");
		req.setHeader("Authorization", "Bearer " + accessToken);

		JsonNode json = Json.newObject().put("username", username).put("description", description);

		Promise<Todo> jsonPromise = req.post(json).map(new Function<WSResponse, Todo>() {
			public Todo apply(WSResponse response) {
				JsonNode json = response.asJson();
				Todo todo = new Todo();
				todo.setId(json.get("id").asLong());
				todo.setDescription(json.get("description").asText());
				todo.setUsername(json.get("username").asText());
				return todo;
			}
		});

		return jsonPromise.get(5000);
	}
	
	public static Todo edit(String accessToken, Long id, String description) {
		WSRequestHolder req = WS.url(OAUTH_RESOURCE_SERVER_URL + "/rest/todos/edit");
		req.setHeader("Authorization", "Bearer " + accessToken);

		JsonNode json = Json.newObject().put("id", id).put("description", description);

		Promise<Todo> jsonPromise = req.post(json).map(new Function<WSResponse, Todo>() {
			public Todo apply(WSResponse response) {
				JsonNode json = response.asJson();
				Todo todo = new Todo();
				todo.setId(json.get("id").asLong());
				todo.setDescription(json.get("description").asText());
				todo.setUsername(json.get("username").asText());
				return todo;
			}
		});

		return jsonPromise.get(5000);
	}

	public static String delete(String accessToken, String username, Long id) {
		WSRequestHolder req = WS.url(OAUTH_RESOURCE_SERVER_URL + "/rest/todos/" + id + "/delete");
		req.setHeader("Authorization", "Bearer " + accessToken);

		Promise<String> jsonPromise = req.post("").map(new Function<WSResponse, String>() {
			public String apply(WSResponse response) {
				JsonNode json = response.asJson();
				return json.asText();
			}
		});

		return jsonPromise.get(5000);

	}
}
