package tech.abishek.aiexamples;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import static org.springframework.util.MimeTypeUtils.IMAGE_JPEG;

@SpringBootApplication
public class AiExamplesApplication {

    @Value("classpath:/images.jpeg")
    Resource birdImage;

    public static void main(String[] args) {
        SpringApplication.run(AiExamplesApplication.class, args);
    }

    @Bean
    ApplicationRunner multiModel(ChatClient chatClient) {
        return args -> {
            var options = OpenAiChatOptions.builder().withModel(OpenAiApi.ChatModel.GPT_4_O.getValue()).build();
            var media = new Media(IMAGE_JPEG, birdImage);
            var response = chatClient.call(new Prompt(new UserMessage("Whats the name of this bird?", media), options));
            var content = response.getResult().getOutput().getContent();
            System.out.println(content);
        };
    }

}
