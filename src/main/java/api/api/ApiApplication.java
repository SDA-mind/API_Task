package api.api;

import api.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class ApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<User> request = new HttpEntity<>(headers);
        ResponseEntity<String> responseList
                = restTemplate.exchange("http://91.241.64.178:7081/api/users", HttpMethod.GET, request, String.class);
        List<String> cookieList = responseList.getHeaders().getValuesAsList("Set-Cookie");

        HttpHeaders headersAdd = new HttpHeaders();
        headersAdd.add("Cookie", cookieList.get(0));
        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 25);
        HttpEntity<User> AddRequest = new HttpEntity<>(user, headersAdd);
        ResponseEntity<String> response
                = restTemplate.exchange("http://91.241.64.178:7081/api/users", HttpMethod.POST, AddRequest, String.class);
        String code = response.getBody();

        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> EditRequest = new HttpEntity<>(user, headersAdd);
        ResponseEntity<String> editResponse
                = restTemplate.exchange("http://91.241.64.178:7081/api/users", HttpMethod.PUT, EditRequest, String.class);
        code = code.concat(editResponse.getBody());

        HttpEntity<User> DeleteRequest = new HttpEntity<>(headersAdd);
        ResponseEntity<String> deleteResponse
                = restTemplate.exchange("http://91.241.64.178:7081/api/users/3", HttpMethod.DELETE, DeleteRequest, String.class);
        code = code.concat(deleteResponse.getBody());
        System.out.println(code);
    }
}
