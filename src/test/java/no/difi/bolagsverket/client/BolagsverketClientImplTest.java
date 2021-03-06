package no.difi.bolagsverket.client;

import no.difi.bolagsverket.config.ClientProperties;
import no.difi.bolagsverket.model.Identifier;
import no.difi.bolagsverket.request.RequestProvider;
import no.difi.bolagsverket.xml.GetProduktResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BolagsverketClientImplTest {

    private BolagsverketClient target;
    @Mock
    private WebServiceTemplate templateMock;
    @Mock
    private ClientProperties propertiesMock;
    @Mock
    private RequestProvider requestProviderMock;

    @Before
    public void setUp() {
        when(propertiesMock.getCertId()).thenReturn("validCertId");
        when(propertiesMock.getUserId()).thenReturn("validUserId");
        target = new BolagsverketClientImpl(propertiesMock, templateMock, requestProviderMock);
    }

    @Test(expected = NullPointerException.class)
    public void getProdukt_IdentifierIsNull_ShouldThrow() {
        target.getProdukt(null);
    }

    @Test
    public void getProdukt_XmlQueryIsEmpty_ResultShouldBeEmpty() {
        when(requestProviderMock.getRequest(any(Identifier.class))).thenReturn(Optional.empty());
        Optional<GetProduktResponse> result = target.getProdukt(Identifier.from("2021005489"));
        assertFalse(result.isPresent());
    }
}
