package github.hotstu.ezuniplugin.base.compat;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class SafeWrapper {

    private static Class unsafeClass;
    private static Object unsafeInstance;

    private SafeWrapper() {
        //no instance
    }

    /** ==============================================*/
    /** ====================内存块操作==================*/
    /** ==============================================*/

    /**
     * Allocates a new block of native memory, of the given size in bytes.  The
     * contents of the memory are uninitialized; they will generally be
     * garbage.  The resulting native pointer will never be zero, and will be
     * aligned for all value types.  Dispose of this memory by calling {@link
     * #freeMemory}, or resize it with {@link #reallocateMemory}.
     *
     * <em>Note:</em> It is the resposibility of the caller to make
     * sure arguments are checked before the methods are called. While
     * some rudimentary checks are performed on the input, the checks
     * are best effort and when performance is an overriding priority,
     * as when methods of this class are optimized by the runtime
     * compiler, some or all checks (if any) may be elided. Hence, the
     * caller must not rely on the checks and corresponding
     * exceptions!
     *
     * @throws RuntimeException if the size is negative or too large
     *         for the native size_t type
     *
     * @throws OutOfMemoryError if the allocation is refused by the system
     *
     * @see #getByte(long)
     * @see #putByte(long, byte)
     */
    public static long allocateMemory(long bytes) {
        long address = 0;
        try {
            Method allocateMemoryMethod = unsafeClass().getDeclaredMethod("allocateMemory", long.class);
            address = (long) allocateMemoryMethod.invoke(unsafeInstance(), bytes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return address;
    }

    /**
     * Resizes a new block of native memory, to the given size in bytes.  The
     * contents of the new block past the size of the old block are
     * uninitialized; they will generally be garbage.  The resulting native
     * pointer will be zero if and only if the requested size is zero.  The
     * resulting native pointer will be aligned for all value types.  Dispose
     * of this memory by calling {@link #freeMemory}, or resize it with {@link
     * #reallocateMemory}.  The address passed to this method may be null, in
     * which case an allocation will be performed.
     *
     * <em>Note:</em> It is the resposibility of the caller to make
     * sure arguments are checked before the methods are called. While
     * some rudimentary checks are performed on the input, the checks
     * are best effort and when performance is an overriding priority,
     * as when methods of this class are optimized by the runtime
     * compiler, some or all checks (if any) may be elided. Hence, the
     * caller must not rely on the checks and corresponding
     * exceptions!
     *
     * @throws RuntimeException if the size is negative or too large
     *         for the native size_t type
     *
     * @throws OutOfMemoryError if the allocation is refused by the system
     *
     * @see #allocateMemory
     */
    public static long reallocateMemory(long address, long bytes) {
        long result = 0;
        try {
            Method allocateMemoryMethod = unsafeClass().getDeclaredMethod("reallocateMemory", long.class, long.class);
            result = (long) allocateMemoryMethod.invoke(unsafeInstance(), address, bytes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Disposes of a block of native memory, as obtained from {@link
     * #allocateMemory} or {@link #reallocateMemory}.  The address passed to
     * this method may be null, in which case no action is taken.
     *
     * <em>Note:</em> It is the resposibility of the caller to make
     * sure arguments are checked before the methods are called. While
     * some rudimentary checks are performed on the input, the checks
     * are best effort and when performance is an overriding priority,
     * as when methods of this class are optimized by the runtime
     * compiler, some or all checks (if any) may be elided. Hence, the
     * caller must not rely on the checks and corresponding
     * exceptions!
     *
     * @throws RuntimeException if any of the arguments is invalid
     *
     * @see #allocateMemory
     */
    public static void freeMemory(long address) {
        try {
            Method freeMemoryMethod = unsafeClass().getDeclaredMethod("freeMemory", long.class);
            freeMemoryMethod.invoke(unsafeInstance(), address);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches a value from a given memory address.  If the address is zero, or
     * does not point into a block obtained from {@link #allocateMemory}, the
     * results are undefined.
     *
     * @see #allocateMemory
     */
    public static byte getByte(long address) {
        byte result = 0;
        try {
            Method allocateMemoryMethod = unsafeClass().getDeclaredMethod("getByte", long.class);
            result = (byte) allocateMemoryMethod.invoke(unsafeInstance(), address);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Stores a value into a given memory address.  If the address is zero, or
     * does not point into a block obtained from {@link #allocateMemory}, the
     * results are undefined.
     *
     * @see #getByte(long)
     */
    public static void putByte(long address, byte x) {
        try {
            Method allocateMemoryMethod = unsafeClass().getDeclaredMethod("putByte", long.class, byte.class);
            allocateMemoryMethod.invoke(unsafeInstance(), address, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从内存中读取一个数组
     *
     * @param startAddress  内存起始地址
     * @param len           要读取的数组的长度
     * @return              返回从内存中读取到的数组
     */
    public static byte[] readByteArray(long startAddress, int len) {
        byte[] bytes = new byte[len];

        long currentAddress = startAddress;
        long lastAddress = startAddress + len;
        while(currentAddress < lastAddress) {
            int currentIndex = (int) (currentAddress - startAddress);
            bytes[currentIndex] = getByte(currentAddress);
            currentAddress++;
        }

        return bytes;
    }

    /**
     * 把一个byte[]放到指定的内存块中
     *
     * @param startAddress  内存块的起始位置
     * @param bytes         要被存放的内存
     */
    public static void putByteArray(long startAddress, byte[] bytes) {
        long currentAddress = startAddress;
        long lastAddress = startAddress + bytes.length;
        while(currentAddress < lastAddress) {
            int currentIndex = (int) (currentAddress - startAddress);
            putByte(currentAddress, bytes[currentIndex]);
            currentAddress++;
        }
    }

    /** @see #getByte(long) */
    public static short getShort(long address) {
        short result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getShort", long.class);
            result = (short) method.invoke(unsafeInstance(), address);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putByte(long, byte) */
    public static void putShort(long address, short x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putShort", long.class, short.class);
            method.invoke(unsafeInstance(), address, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getByte(long) */
    public static char getChar(long address) {
        char result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getChar", long.class);
            result = (char) method.invoke(unsafeInstance(), address);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putByte(long, byte) */
    public static void putChar(long address, char x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putChar", long.class, char.class);
            method.invoke(unsafeInstance(), address, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getByte(long) */
    public static int getInt(long address) {
        int result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getInt", long.class);
            result = (int) method.invoke(unsafeInstance(), address);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putByte(long, byte) */
    public static void putInt(long address, int x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putInt", long.class, int.class);
            method.invoke(unsafeInstance(), address, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getByte(long) */
    public static long getLong(long address) {
        long result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getLong", long.class);
            result = (long) method.invoke(unsafeInstance(), address);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putByte(long, byte) */
    public static void putLong(long address, long x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putLong", long.class, long.class);
            method.invoke(unsafeInstance(), address, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getByte(long) */
    public static float getFloat(long address) {
        float result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getFloat", long.class);
            result = (float) method.invoke(unsafeInstance(), address);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putByte(long, byte) */
    public static void putFloat(long address, float x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putFloat", long.class, float.class);
            method.invoke(unsafeInstance(), address, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getByte(long) */
    public static double getDouble(long address) {
        double result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getDouble", long.class);
            result = (double) method.invoke(unsafeInstance(), address);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putByte(long, byte) */
    public static void putDouble(long address, double x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putDouble", long.class, double.class);
            method.invoke(unsafeInstance(), address, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }






    /** ==============================================*/
    /** ====================对象操作====================*/
    /** ==============================================*/

    /**
     * Allocates an instance but does not run any constructor.
     * Initializes the class if it has not yet been.
     */
    public static <T> T allocateInstance(Class<T> klass) {

        T result = null;

        try {
            Method allocateInstanceMethod = unsafeClass().getDeclaredMethod("allocateInstance", Class.class);
            result = (T) allocateInstanceMethod.invoke(unsafeInstance(), klass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return result;
    }

    /** @see #getInt(Object, long) */
    public static byte getByte(Object o, long offset) {
        byte result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getByte", Object.class, long.class);
            result = (byte) method.invoke(unsafeInstance(), o, offset);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putInt(Object, long, int) */
    public static void putByte(Object o, long offset, byte x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putByte", Object.class, long.class, byte.class);
            method.invoke(unsafeInstance(), o, offset, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches a value from a given Java variable.
     * More specifically, fetches a field or array element within the given
     * object {@code o} at the given offset, or (if {@code o} is null)
     * from the memory address whose numerical value is the given offset.
     * <p>
     * The results are undefined unless one of the following cases is true:
     * <ul>
     * <li>The offset was obtained from {@link #objectFieldOffset} on
     * the {@link Field} of some Java field and the object
     * referred to by {@code o} is of a class compatible with that
     * field's class.
     *
     * <li>The offset and object reference {@code o} (either null or
     * non-null) were both obtained via {@link #staticFieldOffset}
     * and {@link #staticFieldBase} (respectively) from the
     * reflective {@link Field} representation of some Java field.
     *
     * <li>The object referred to by {@code o} is an array, and the offset
     * is an integer of the form {@code B+N*S}, where {@code N} is
     * a valid index into the array, and {@code B} and {@code S} are
     * the values obtained by {@link #arrayBaseOffset} and {@link
     * #arrayIndexScale} (respectively) from the array's class.  The value
     * referred to is the {@code N}<em>th</em> element of the array.
     *
     * </ul>
     * <p>
     * If one of the above cases is true, the call references a specific Java
     * variable (field or array element).  However, the results are undefined
     * if that variable is not in fact of the type returned by this method.
     * <p>
     * This method refers to a variable by means of two parameters, and so
     * it provides (in effect) a <em>double-register</em> addressing mode
     * for Java variables.  When the object reference is null, this method
     * uses its offset as an absolute address.  This is similar in operation
     * to methods such as {@link #getInt(long)}, which provide (in effect) a
     * <em>single-register</em> addressing mode for non-Java variables.
     * However, because Java variables may have a different layout in memory
     * from non-Java variables, programmers should not assume that these
     * two addressing modes are ever equivalent.  Also, programmers should
     * remember that offsets from the double-register addressing mode cannot
     * be portably confused with longs used in the single-register addressing
     * mode.
     *
     * @param o Java heap object in which the variable resides, if any, else
     *        null
     * @param offset indication of where the variable resides in a Java heap
     *        object, if any, else a memory address locating the variable
     *        statically
     * @return the value fetched from the indicated Java variable
     * @throws RuntimeException No defined exceptions are thrown, not even
     *         {@link NullPointerException}
     */
    public static int getInt(Object o, long offset) {
        int result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getInt", Object.class, long.class);
            result = (int) method.invoke(unsafeInstance(), o, offset);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Stores a value into a given Java variable.
     * <p>
     * The first two parameters are interpreted exactly as with
     * {@link #getInt(Object, long)} to refer to a specific
     * Java variable (field or array element).  The given value
     * is stored into that variable.
     * <p>
     * The variable must be of the same type as the method
     * parameter {@code x}.
     *
     * @param o Java heap object in which the variable resides, if any, else
     *        null
     * @param offset indication of where the variable resides in a Java heap
     *        object, if any, else a memory address locating the variable
     *        statically
     * @param x the value to store into the indicated Java variable
     * @throws RuntimeException No defined exceptions are thrown, not even
     *         {@link NullPointerException}
     */
    public static void putInt(Object o, long offset, int x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putInt", Object.class, long.class, int.class);
            method.invoke(unsafeInstance(), o, offset, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fetches a reference value from a given Java variable.
     * @see #getInt(Object, long)
     */
    public static Object getObject(Object o, long offset) {
        Object result = null;
        try {
            Method method = unsafeClass().getDeclaredMethod("getObject", Object.class, long.class);
            result = method.invoke(unsafeInstance(), o, offset);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Stores a reference value into a given Java variable.
     * <p>
     * Unless the reference {@code x} being stored is either null
     * or matches the field type, the results are undefined.
     * If the reference {@code o} is non-null, card marks or
     * other store barriers for that object (if the VM requires them)
     * are updated.
     * @see #putInt(Object, long, int)
     */
    public static void putObject(Object o, long offset, Object x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putObject", Object.class, long.class, Object.class);
            method.invoke(unsafeInstance(), o, offset, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /** @see #getInt(Object, long) */
    public static boolean getBoolean(Object o, long offset) {
        boolean result = false;
        try {
            Method method = unsafeClass().getDeclaredMethod("getBoolean", Object.class, long.class);
            result = (boolean) method.invoke(unsafeInstance(), o, offset);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putInt(Object, long, int) */
    public static void putBoolean(Object o, long offset, boolean x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putBoolean", Object.class, long.class, boolean.class);
            method.invoke(unsafeInstance(), o, offset, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getInt(Object, long) */
    public static short getShort(Object o, long offset) {
        short result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getShort", Object.class, long.class);
            result = (short) method.invoke(unsafeInstance(), o, offset);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putInt(Object, long, int) */
    public static void putShort(Object o, long offset, short x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putShort", Object.class, long.class, short.class);
            method.invoke(unsafeInstance(), o, offset, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getInt(Object, long) */
    public static char getChar(Object o, long offset) {
        char result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getChar", Object.class, long.class);
            result = (char) method.invoke(unsafeInstance(), o, offset);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putInt(Object, long, int) */
    public static void putChar(Object o, long offset, char x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putChar", Object.class, long.class, char.class);
            method.invoke(unsafeInstance(), o, offset, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getInt(Object, long) */
    public static long getLong(Object o, long offset) {
        long result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getLong", Object.class, long.class);
            result = (long) method.invoke(unsafeInstance(), o, offset);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putInt(Object, long, int) */
    public static void putLong(Object o, long offset, long x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putLong", Object.class, long.class, long.class);
            method.invoke(unsafeInstance(), o, offset, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getInt(Object, long) */
    public static float getFloat(Object o, long offset) {
        float result = 0f;
        try {
            Method method = unsafeClass().getDeclaredMethod("getFloat", Object.class, long.class);
            result = (float) method.invoke(unsafeInstance(), o, offset);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putInt(Object, long, int) */
    public static void putFloat(Object o, long offset, float x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putFloat", Object.class, long.class, float.class);
            method.invoke(unsafeInstance(), o, offset, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** @see #getInt(Object, long) */
    public static double getDouble(Object o, long offset) {
        double result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("getDouble", Object.class, long.class);
            result = (double) method.invoke(unsafeInstance(), o, offset);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /** @see #putInt(Object, long, int) */
    public static void putDouble(Object o, long offset, double x) {
        try {
            Method method = unsafeClass().getDeclaredMethod("putDouble", Object.class, long.class, double.class);
            method.invoke(unsafeInstance(), o, offset, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }




    /** ==============================================*/
    /** ================偏移量操作======================*/
    /** ==============================================*/
    /**
     * Reports the location of a given field in the storage allocation of its
     * class.  Do not expect to perform any sort of arithmetic on this offset;
     * it is just a cookie which is passed to the unsafe heap memory accessors.
     *
     * <p>Any given field will always have the same offset and base, and no
     * two distinct fields of the same class will ever have the same offset
     * and base.
     *
     * <p>As of 1.4.1, offsets for fields are represented as long values,
     * although the Sun JVM does not use the most significant 32 bits.
     * However, JVM implementations which store static fields at absolute
     * addresses can use long offsets and null base pointers to express
     * the field locations in a form usable by {@link #getInt(Object,long)}.
     * Therefore, code which will be ported to such JVMs on 64-bit platforms
     * must preserve all bits of static field offsets.
     * @see #getInt(Object, long)
     */
    public static long objectFieldOffset(Field field) {
        long result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("objectFieldOffset", Field.class);
            result = (long) method.invoke(unsafeInstance(), field);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Reports the location of a given static field, in conjunction with {@link
     * #staticFieldBase}.
     * <p>Do not expect to perform any sort of arithmetic on this offset;
     * it is just a cookie which is passed to the unsafe heap memory accessors.
     *
     * <p>Any given field will always have the same offset, and no two distinct
     * fields of the same class will ever have the same offset.
     *
     * <p>As of 1.4.1, offsets for fields are represented as long values,
     * although the Sun JVM does not use the most significant 32 bits.
     * It is hard to imagine a JVM technology which needs more than
     * a few bits to encode an offset within a non-array object,
     * However, for consistency with other methods in this class,
     * this method reports its result as a long value.
     * @see #getInt(Object, long)
     */
    public static long staticFieldOffset(Field field) {
        long result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("staticFieldOffset", Field.class);
            result = (long) method.invoke(unsafeInstance(), field);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Reports the location of a given static field, in conjunction with {@link
     * #staticFieldOffset}.
     * <p>Fetch the base "Object", if any, with which static fields of the
     * given class can be accessed via methods like {@link #getInt(Object,
     * long)}.  This value may be null.  This value may refer to an object
     * which is a "cookie", not guaranteed to be a real Object, and it should
     * not be used in any way except as argument to the get and put routines in
     * this class.
     */
    public static Object staticFieldBase(Field field) {
        Object result = false;
        try {
            Method method = unsafeClass().getDeclaredMethod("staticFieldBase", Field.class);
            result = method.invoke(unsafeInstance(), field);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Reports the offset of the first element in the storage allocation of a
     * given array class.  If {@link #arrayIndexScale} returns a non-zero value
     * for the same class, you may use that scale factor, together with this
     * base offset, to form new offsets to access elements of arrays of the
     * given class.
     *
     * @see #getInt(Object, long)
     * @see #putInt(Object, long, int)
     */
    public static int arrayBaseOffset(Class<?> arrayClass) {
        int result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("arrayBaseOffset", Class.class);
            result = (int) method.invoke(unsafeInstance(), arrayClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Reports the scale factor for addressing elements in the storage
     * allocation of a given array class.  However, arrays of "narrow" types
     * will generally not work properly with accessors like {@link
     * #getByte(Object, long)}, so the scale factor for such classes is reported
     * as zero.
     *
     * @see #arrayBaseOffset
     * @see #getInt(Object, long)
     * @see #putInt(Object, long, int)
     */
    public static int arrayIndexScale(Class<?> arrayClass) {
        int result = 0;
        try {
            Method method = unsafeClass().getDeclaredMethod("arrayIndexScale", Class.class);
            result = (int) method.invoke(unsafeInstance(), arrayClass);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    /** ==============================================*/
    /** ==================CAS操作======================*/
    /** ==============================================*/
    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public static boolean compareAndSwapObject(Object o, long offset, Object expected, Object x) {
        boolean result = false;
        try {
            Method method = unsafeClass().getDeclaredMethod("compareAndSwapObject", Object.class, long.class, Object.class, Object.class);
            result = (boolean) method.invoke(unsafeInstance(), o, offset, expected, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public static boolean compareAndSwapInt(Object o, long offset, int expected, int x) {
        boolean result = false;
        try {
            Method method = unsafeClass().getDeclaredMethod("compareAndSwapInt", Object.class, long.class, int.class, int.class);
            result = (boolean) method.invoke(unsafeInstance(), o, offset, expected, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Atomically updates Java variable to {@code x} if it is currently
     * holding {@code expected}.
     *
     * <p>This operation has memory semantics of a {@code volatile} read
     * and write.  Corresponds to C11 atomic_compare_exchange_strong.
     *
     * @return {@code true} if successful
     */
    public static boolean compareAndSwapLong(Object o, long offset, long expected, long x) {
        boolean result = false;
        try {
            Method method = unsafeClass().getDeclaredMethod("compareAndSwapLong", Object.class, long.class, long.class, long.class);
            result = (boolean) method.invoke(unsafeInstance(), o, offset, expected, x);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * Unblocks the given thread blocked on {@code park}, or, if it is
     * not blocked, causes the subsequent call to {@code park} not to
     * block.  Note: this operation is "unsafe" solely because the
     * caller must somehow ensure that the thread has not been
     * destroyed. Nothing special is usually required to ensure this
     * when called from Java (in which there will ordinarily be a live
     * reference to the thread) but this is not nearly-automatically
     * so when calling from native code.
     *
     * @param thread the thread to unpark.
     */
    public void unpark(Object thread) {
        try {
            Method method = unsafeClass().getDeclaredMethod("park", Object.class);
            method.invoke(unsafeInstance(), thread);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Blocks current thread, returning when a balancing
     * {@code unpark} occurs, or a balancing {@code unpark} has
     * already occurred, or the thread is interrupted, or, if not
     * absolute and time is not zero, the given time nanoseconds have
     * elapsed, or if absolute, the given deadline in milliseconds
     * since Epoch has passed, or spuriously (i.e., returning for no
     * "reason"). Note: This operation is in the Unsafe class only
     * because {@code unpark} is, so it would be strange to place it
     * elsewhere.
     */
    public void park(boolean isAbsolute, long time) {
        try {
            Method method = unsafeClass().getDeclaredMethod("park", boolean.class, long.class);
            method.invoke(unsafeInstance(), isAbsolute, time);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void init() {
        try {
            unsafeClass = Class.forName("sun.misc.Unsafe");
            Field theUnsafeField = unsafeClass.getDeclaredField("theUnsafe");
            boolean orignialAccessible = theUnsafeField.isAccessible();
            theUnsafeField.setAccessible(true);
            unsafeInstance = theUnsafeField.get(null);
            theUnsafeField.setAccessible(orignialAccessible);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Object unsafeInstance() {
        if(unsafeInstance == null) init();
        return unsafeInstance;
    }

    private static Class unsafeClass() {
        if(unsafeClass == null) init();
        return unsafeClass;
    }

}