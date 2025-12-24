package ai.agentscentral.http.route;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PathPatternExtractorTest {

    @Test
    void should_extract_path_pattern_when_no_path_variables_present(){
        //Given
        final String path = "/users/";

        //When
        final PathPattern pathPattern = PathPatternExtractor.extract(path);

        //Then
        assertNotNull(pathPattern);
        assertEquals("^/users/$", pathPattern.pattern().pattern());

        final List<String> variableNames = pathPattern.pathVariableNames();
        assertNotNull(variableNames);
        assertEquals(0, variableNames.size());
    }


    @Test
    void should_extract_path_pattern_when_single_path_variable_is_defined(){
        //Given
        final String path = "/users/{id}";

        //When
        final PathPattern pathPattern = PathPatternExtractor.extract(path);

        //Then
        assertNotNull(pathPattern);
        assertEquals("^/users/([^/]+)$", pathPattern.pattern().pattern());

        final List<String> variableNames = pathPattern.pathVariableNames();
        assertNotNull(variableNames);
        assertEquals(1, variableNames.size());
        assertEquals("id", variableNames.getFirst());
    }


    @Test
    void should_extract_path_pattern_and_when_multiple_path_variables_are_defined(){
        //Given
        final String path = "/users/{id}/addresses/{addressType}/{index}";

        //When
        final PathPattern pathPattern = PathPatternExtractor.extract(path);

        //Then
        assertNotNull(pathPattern);
        assertEquals("^/users/([^/]+)/addresses/([^/]+)/([^/]+)$", pathPattern.pattern().pattern());

        final List<String> variableNames = pathPattern.pathVariableNames();
        assertNotNull(variableNames);
        assertEquals(3, variableNames.size());
        assertEquals("id", variableNames.get(0));
        assertEquals("addressType", variableNames.get(1));
        assertEquals("index", variableNames.get(2));
    }

}