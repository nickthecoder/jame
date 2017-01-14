package uk.co.nickthecoder.jame;

public class PixelFormat
{

    public enum PixelType
    {
        UNKNOWN, INDEX1, INDEX4, INDEX8, PACKED8, PACKED16, PACKED32,
        ARRAYU8, ARRAYU16, ARRAYU32, ARRAYF16, ARRAYF32;

        public boolean isPacked()
        {
            return (this == PACKED8) || (this == PACKED16) || (this == PACKED32);
        }

        public boolean isIndexed()
        {
            return (this == INDEX1) || (this == INDEX4) || (this == INDEX8);
        }

        public boolean isArray()
        {
            return (this == ARRAYU16) || (this == ARRAYU32) || (this == ARRAYF16) || (this == ARRAYF32);
        }
    };

    public interface Order
    {
        public int ordinal();

        public Order withAlpha();
    }

    public enum BitmapOrder implements Order
    {
        NONE, _4321, _1234;

        public Order withAlpha()
        {
            throw new JameRuntimeException("Not applicable to BitmapOrders");
        }
    };

    /** Packed component order, high bit -> low bit. */
    enum PackedOrder implements Order
    {
        NONE, XRGB, RGBX, ARGB, RGBA, XBGR, BGRX, ABGR, BGRA;

        public Order withAlpha()
        {
            if (this == XRGB)
                return ARGB;
            if (this == RGBX)
                return RGBA;
            if (this == XBGR)
                return ABGR;
            if (this == BGRX)
                return BGRA;

            return this;
        }
    };

    /** Array component order, low byte -> high byte. */

    enum ArrayOrder implements Order
    {
        NONE, RGB, RGBA, ARGB, BGR, BGRA, ABGR;

        public Order withAlpha()
        {
            if (this == RGB)
                return RGBA;
            if (this == BGR)
                return ABGR;
            return this;
        }

    };

    public interface Layout
    {
        public int ordinal();
    }

    /** Packed component layout. */

    public enum PackedLayout implements Layout
    {
        NONE, _332, _4444, _1555, _5551, _565, _8888, _2101010, _1010102
    };

    public static int createPixelFormat(int pixelType, int pixelOrder, int pixelLayout, int bits, int bytes)
    {
        return ((1 << 28) | ((pixelType) << 24) | ((pixelOrder) << 20) | ((pixelLayout) << 16) | ((bits) << 8) | ((bytes) << 0));
    }

    public final PixelType pixelType;
    public final Layout layout;
    public final Order order;
    public final int bits;
    public final int bytes;

    public static final PixelFormat RGBA8888 = new PixelFormat(PixelType.PACKED32, PackedOrder.RGBA,
        PackedLayout._8888, 32, 4);

    public static final PixelFormat ABGR8888 = new PixelFormat(PixelType.PACKED32, PackedOrder.ABGR,
        PackedLayout._8888, 32, 4);

    public static final PixelFormat RGBX8888 = new PixelFormat(PixelType.PACKED32, PackedOrder.RGBX,
        PackedLayout._8888, 24, 4);

    /**
     * The value, as used by the low level SDL functions.
     */
    public final int value;

    private static Order getOrder(PixelType pixelType, int order)
    {
        if (pixelType.isPacked()) {
            return PackedOrder.values()[order];
        } else if (pixelType.isArray()) {
            return BitmapOrder.values()[order];
        } else if (pixelType.isIndexed()) {
            return ArrayOrder.values()[order];
        }

        return PackedOrder.NONE;
    }

    public PixelFormat(int flags)
    {
        pixelType = PixelType.values()[(flags >> 24) & 0xf];
        layout = PackedLayout.values()[(flags >> 16) & 0xf];
        order = getOrder(pixelType, (flags >> 20) & 0xf);

        bits = (flags >> 8) & 0xff;
        bytes = (flags) & 0xff;

        value = (1 << 28) | (pixelType.ordinal() << 24) | (order.ordinal() << 20) | (layout.ordinal() << 16)
            | (bits << 8) | bytes;
    }

    public PixelFormat(PixelType pixelType, Order order, Layout layout, int bits, int bytes)
    {
        this.pixelType = pixelType;
        this.layout = layout;
        this.order = order;
        this.bits = bits;
        this.bytes = bytes;

        value = (1 << 28) | (pixelType.ordinal() << 24) | (order.ordinal() << 20) | (layout.ordinal() << 16)
            | (bits << 8) | bytes;
    }

    /**
     * Used to get a PixelFormat that is compatable with {@link Window#getPixelFormat()}, but also has an alpha channel
     * 
     * @return A PixelFormat similar to this one, but with an alpha channel.
     * 
     */
    public PixelFormat withAlpha()
        throws JameRuntimeException
    {
        if (this.pixelType != PixelType.PACKED32) {
            throw new JameRuntimeException("Can only use PixelType.PACKED32");
        }
        if (this.bits < 24) {
            throw new JameRuntimeException("Must use 24 bits or more.");
        }
        if (this.layout != PackedLayout._8888) {
            throw new JameRuntimeException("Only valid with PackedLayout._8888");
        }

        return new PixelFormat(PixelType.PACKED32, this.order.withAlpha(), layout, 32, 4);
    }

    public boolean equals(Object other)
    {
        if (other instanceof PixelFormat) {
            PixelFormat o = (PixelFormat) other;
            return this.value == o.value;
        }
        return false;
    }

}
