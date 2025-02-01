package eg.com.vodafone.config;


import eg.com.vodafone.ResponseDto.UserProfileResponse;
import eg.com.vodafone.model.Customer;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Customer, UserProfileResponse>() {
            @Override
            protected void configure() {
                map().setUserName(source.getUserName());
                map().setEmail(source.getEmail());
                map().setFirstName(source.getFirstName());
                map().setLastName(source.getLastName());
            }
        });


        return modelMapper;
    }

}