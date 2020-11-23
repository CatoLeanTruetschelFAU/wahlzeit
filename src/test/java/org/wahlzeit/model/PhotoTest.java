package org.wahlzeit.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhotoTest extends PhotoSpecification{

    @Override
    protected Photo init() {
        return new Photo();
    }

    @Override
    protected Photo init(PhotoId photoId) {
        return new Photo(photoId);
    }

    @Override
    protected Photo init(ResultSet rset) throws SQLException {
        return new Photo(rset);
    }
}
