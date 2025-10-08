package edu.citadel.cucumber;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ActiveProfilesResolver;
import edu.citadel.main.RestApiApplication;

@SpringBootTest(classes = RestApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(resolver = SpringIntegrationTest.ProfileResolver.class)
public class SpringIntegrationTest {

    public static class ProfileResolver implements ActiveProfilesResolver {
        @Override
        public String[] resolve(Class<?> testClass) {
            // Use profile from system property if set (for CI), otherwise use "test" for local
            String profile = System.getProperty("spring.profiles.active");
            if (profile == null || profile.trim().isEmpty()) {
                // Check environment variable as fallback
                profile = System.getenv("SPRING_PROFILES_ACTIVE");
            }
            return new String[]{profile != null ? profile : "test"};
        }
    }
}