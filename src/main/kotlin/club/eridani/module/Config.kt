package club.eridani.module

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
open class ModuleConfig(var enable: Boolean)


@Serializable(with = IntRangeSerializer::class)
open class IntRangeValue(val value: Int, @Transient val range: IntRange)
open class IntRangeSerializer(val range: IntRange = 0..10) : KSerializer<IntRangeValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("IntRangeValue", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: IntRangeValue) = encoder.encodeInt(value.value)
    override fun deserialize(decoder: Decoder): IntRangeValue = IntRangeValue(decoder.decodeInt(), range)
}

@Serializable(with = DoubleRangeSerializer::class)
open class DoubleRangeValue(val value: Double, @Transient val min: Double, @Transient val max: Double)
open class DoubleRangeSerializer(val min: Double = 0.0, val max: Double = 10.0) : KSerializer<DoubleRangeValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("IntRangeValue", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: DoubleRangeValue) = encoder.encodeDouble(value.value)
    override fun deserialize(decoder: Decoder): DoubleRangeValue = DoubleRangeValue(decoder.decodeDouble(), min, max)
}

@Serializable(with = FloatRangeSerializer::class)
open class FloatRangeValue(val value: Float, @Transient val min: Float, @Transient val max: Float)
open class FloatRangeSerializer(val min: Float = 0f, val max: Float = 10f) : KSerializer<FloatRangeValue> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("FloatRangeValue", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: FloatRangeValue) = encoder.encodeFloat(value.value)
    override fun deserialize(decoder: Decoder): FloatRangeValue = FloatRangeValue(decoder.decodeFloat(), min, max)
}