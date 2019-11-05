package ir.traap.tractor.android.conf;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class RealmMigrations implements RealmMigration
{
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion)
    {
        RealmSchema schema = realm.getSchema();

//        if (oldVersion == 0)
//        {
//            RealmObjectSchema GuildsSchema = schema.create("GuildsDB")
//                    .addField("guildId", Integer.class)
//                    .setNullable("guildId", false)
//                    .addField("guildName", String.class)
//                    .setNullable("guildName", false);
//
//            oldVersion++;
//        }

    }

    @Override
    public boolean equals(Object obj)
    {
//            return super.equals(obj);
        return (obj instanceof RealmMigrations);
    }

    @Override
    public int hashCode()
    {
//            return super.hashCode();
        return 37;
    }

}
