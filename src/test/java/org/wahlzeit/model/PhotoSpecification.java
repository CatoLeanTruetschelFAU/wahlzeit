package org.wahlzeit.model;

import org.junit.*;
import org.mockito.*;
import org.wahlzeit.services.Language;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class PhotoSpecification {

    private static final double DEFAULT_TOLERANCE = 0.000001;

    protected static final int EXPECTED_ID = 1;
    protected static final int EXPECTED_OWNER_ID = 5;
    protected static final String EXPECTED_OWNER_NAME = "EXPECTED_OWNER_NAME";
    protected static final Boolean EXPECTED_OWNER_NOTIFY_ABOUT_PRAISE = true;
    protected static final String EXPECTED_OWNER_EMAIL_ADDRESS = "user@domain.de";
    protected static final int EXPECTED_OWNER_LANGUAGE = Language.JAPANESE.asInt();
    protected static final String EXPECTED_OWNER_HOME_PAGE = "http://localhost:8080/wahlzeit/upload.html";
    protected static final int EXPECTED_WIDTH= 200;
    protected static final int EXPECTED_HEIGHT = 150;
    protected static final String EXPECTED_TAGS = new Tags("EXPECTED_TAGS").asString();
    protected static final int EXPECTED_STATUS = PhotoStatus.FLAGGED.asInt();
    protected static final int EXPECTED_PRAISE_SUM = 10;
    protected static final int EXPECTED_NO_VOTES = 5;
    protected static final long EXPECTED_CREATION_TIME = 400;
    protected static final String EXPECTED_LOCATION = new Location(new CartesianCoordinate(1, 2, 3)).asString();


    protected abstract Photo init();
    protected abstract Photo init(PhotoId photoId);
    protected abstract Photo init(ResultSet rset) throws SQLException;

    protected ResultSet buildResultSet() throws SQLException {
        ResultSet rset = Mockito.mock(ResultSet.class);
        Mockito.when(rset.getInt("id")).thenReturn(EXPECTED_ID);
        Mockito.when( rset.getInt("owner_id")).thenReturn(EXPECTED_OWNER_ID);
        Mockito.when( rset.getString("owner_name")).thenReturn(EXPECTED_OWNER_NAME);
        Mockito.when( rset.getBoolean("owner_notify_about_praise")).thenReturn(EXPECTED_OWNER_NOTIFY_ABOUT_PRAISE);
        Mockito.when(rset.getString("owner_email_address")).thenReturn(EXPECTED_OWNER_EMAIL_ADDRESS);
        Mockito.when(rset.getInt("owner_language")).thenReturn(EXPECTED_OWNER_LANGUAGE);
        Mockito.when(rset.getString("owner_home_page")).thenReturn(EXPECTED_OWNER_HOME_PAGE);
        Mockito.when(rset.getInt("width")).thenReturn(EXPECTED_WIDTH);
        Mockito.when( rset.getInt("height")).thenReturn(EXPECTED_HEIGHT);
        Mockito.when(rset.getString("tags")).thenReturn(EXPECTED_TAGS);
        Mockito.when(rset.getInt("status")).thenReturn(EXPECTED_STATUS);
        Mockito.when( rset.getInt("praise_sum")).thenReturn(EXPECTED_PRAISE_SUM);
        Mockito.when(rset.getInt("no_votes")).thenReturn(EXPECTED_NO_VOTES);
        Mockito.when(rset.getLong("creation_time")).thenReturn(EXPECTED_CREATION_TIME);
        Mockito.when(rset.getString("location")).thenReturn(EXPECTED_LOCATION);

        return rset;
    }

    @Test
    public void InitTest() {
        // Arrange
        // -

        // Act
        init();

        // Assert
        // Just assert that no exception are thrown
    }

    @Test
    public void InitWithIdTest() {
        // Arrange
        PhotoId expectedId = PhotoId.getIdFromInt(22);

        // Act
        Photo subject = init(expectedId);

        // Assert
        Assert.assertEquals(expectedId, subject.id);
    }

    @Test
    public void InitWithResultSetTest() throws SQLException {
        // Arrange
        ResultSet rset = buildResultSet();
        Coordinate expectedCoordinate = Location.parse(EXPECTED_LOCATION).getCoordinate();

        // Act
        Photo subject = init(rset);

        // Assert
        Assert.assertEquals(EXPECTED_ID, subject.getId().asInt());
        Assert.assertEquals(EXPECTED_OWNER_ID, subject.getOwnerId());
        Assert.assertEquals(EXPECTED_OWNER_NAME, subject.getOwnerName());
        Assert.assertEquals(EXPECTED_OWNER_NOTIFY_ABOUT_PRAISE, subject.getOwnerNotifyAboutPraise());
        Assert.assertEquals(EXPECTED_OWNER_EMAIL_ADDRESS, subject.getOwnerEmailAddress().asString());
        Assert.assertEquals(EXPECTED_OWNER_LANGUAGE, subject.getOwnerLanguage().asInt());
        Assert.assertEquals(EXPECTED_OWNER_HOME_PAGE, subject.getOwnerHomePage().toString());
        Assert.assertEquals(EXPECTED_WIDTH, subject.getWidth());
        Assert.assertEquals(EXPECTED_HEIGHT, subject.getHeight());
        Assert.assertEquals(EXPECTED_TAGS, subject.getTags().asString());
        Assert.assertEquals(EXPECTED_STATUS, subject.getStatus().asInt());
        Assert.assertEquals((float)EXPECTED_PRAISE_SUM / EXPECTED_NO_VOTES, subject.getPraise(), DEFAULT_TOLERANCE);
        Assert.assertEquals(EXPECTED_CREATION_TIME, subject.getCreationTime());
        Assert.assertEquals(expectedCoordinate, subject.getLocation().getCoordinate());
    }
}
