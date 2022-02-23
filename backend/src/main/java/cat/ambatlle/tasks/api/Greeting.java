package cat.ambatlle.tasks.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Greeting {

    @JsonProperty
    private String greeting;

    public Greeting(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }
}
