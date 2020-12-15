package io.github.lexa.core.schema.profiles;

/**
 * Created by koushikr on 21/09/17.
 */
public enum EntityType {

    CATEGORY{
        public <T> T accept(EntityTypeVisitor<T> entityTypeVisitor) throws Exception {
            return entityTypeVisitor.visitCategory();
        }
    },

    MANIFEST{
        public <T> T accept(EntityTypeVisitor<T> entityTypeVisitor) throws Exception {
            return entityTypeVisitor.visitManifest();
        }
    };

    public abstract <T> T accept(EntityTypeVisitor<T> entityTypeVisitor) throws Exception;

}
