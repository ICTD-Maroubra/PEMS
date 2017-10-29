package org.maroubra.pemsserver.database;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class NaiveObjectCodec implements Codec<Object> {

    private static final Logger log = LoggerFactory.getLogger(NaiveObjectCodec.class);

    private final CodecRegistry codecRegistry;

    public NaiveObjectCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public Object decode(BsonReader reader, DecoderContext decoderContext) {
        BsonType type = reader.getCurrentBsonType();

        if (type == BsonType.BOOLEAN)
            return reader.readBoolean();

        if (type == BsonType.INT32)
            return reader.readInt32();

        if (type == BsonType.INT64)
            return reader.readInt64();

        if (type == BsonType.DOUBLE)
            return reader.readDouble();

        if (type == BsonType.DATE_TIME)
            return reader.readDateTime();

        if (type == BsonType.STRING)
            return reader.readString();

        reader.readUndefined();

        return null;
    }

    @Override
    public void encode(BsonWriter writer, Object value, EncoderContext encoderContext) {
        Class<?> clazz = value.getClass();


        if (clazz == Integer.class) {
            codecRegistry.get(Integer.class).encode(writer, (Integer) value, encoderContext);
        }

        else if (clazz == Float.class) {
            codecRegistry.get(Float.class).encode(writer, (Float) value, encoderContext);
        }

        else if (clazz == Double.class) {
            codecRegistry.get(Double.class).encode(writer, (Double) value, encoderContext);
        }

        else if (clazz == Date.class) {
            codecRegistry.get(Date.class).encode(writer, (Date) value, encoderContext);
        }

        else if (clazz == String.class) {
            codecRegistry.get(String.class).encode(writer, (String) value, encoderContext);
        }

        else if (clazz == Boolean.class) {
            codecRegistry.get(Boolean.class).encode(writer, (Boolean) value, encoderContext);
        }

        else {
            log.error("Cannot determine object type - all hope is lost. Writing undefined value.");
            writer.writeUndefined();
        }
    }

    @Override
    public Class<Object> getEncoderClass() {
        return Object.class;
    }
}
