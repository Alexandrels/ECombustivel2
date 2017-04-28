package br.com.ale.ecombustivel.migration;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by alexandre on 10/04/17.
 */

public class MigrationMyData implements RealmMigration {

    public static final Long VERSION = 1L;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

        RealmSchema schema = realm.getSchema();

       // if (oldVersion == 0) {
      //      schema.get("Guitar").addField("strings", String.class);
      //      oldVersion++;
      //  }
    }
}
