package org.kpmp.ingest.redcap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bson.Document;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoCollection;

public class REDCapIngestRepositoryTest {

	@Mock
	private MongoTemplate mongoTemplate;
	private REDCapIngestRepository repository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		repository = new REDCapIngestRepository(mongoTemplate);
	}

	@After
	public void tearDown() throws Exception {
		repository = null;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSaveDump() {
		MongoCollection<Document> mongoCollection = mock(MongoCollection.class);
		when(mongoTemplate.getCollection("redcap")).thenReturn(mongoCollection);
		JSONObject jsonDump = mock(JSONObject.class);
		when(jsonDump.toString()).thenReturn("{'field1': 'value1'}");

		repository.saveDump(jsonDump);
		ArgumentCaptor<Document> documentCaptor = ArgumentCaptor.forClass(Document.class);
		verify(mongoCollection).insertOne(documentCaptor.capture());
		Document actualDocument = documentCaptor.getValue();
		assertEquals("value1", actualDocument.get("field1"));
		assertEquals(true, actualDocument.containsKey("created_at"));
	}

}
