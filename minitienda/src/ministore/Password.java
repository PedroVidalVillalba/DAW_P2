package ministore;
/**
 * Clase que encripta y guarda la contraseña introducida por el usuario
 */
public class Password {
    private Integer256 value;

    public Password(String user, String password) {
        this.value = new Integer256(password);
        this.value.cipher(user);
    }

    /**
     * Método que compara si dos contraseñas son iguales
     * @param savedPassword Contraseña encriptada guardada
     * @return true si son iguales y false en caso contrario
     */
    public boolean compareToSaved(String savedPassword) {
        return this.toString().equals(savedPassword);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }

        Password other = (Password) object;

        if (this.value == null) {
            return false;
        }

        return this.value.equals(other.value);
    }

    public String toString() {
        return value.toString();
    }

    /**
     * Clase creada para almacenar el valor de la contraseña encriptada
     */
    private class Integer256 {
        private final long[] number = new long[4];

        public Integer256(String string) {
            if (string == null) {
                return;
            }
            byte[] bytes = string.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                number[3] ^= (number[3] << 8) | (number[2] >>> 56);
                number[2] += (number[2] << 8) | (number[1] >>> 56);
                number[1] ^= (number[1] << 8) | (number[0] >>> 56);
                number[0] += (number[0] << 8) | bytes[i];	// Añadir el nuevo carácter al final
            }
        }

        /**
         * Método que encripta la contraseña. Para más información sobre cómo lo hace, consulta la memoria
         * Basado en el vídeo "Chacha Cipher - Computerphile"
         */
        public void cipher(String seed) {
            /* Include the seed */
            byte[] bytes = seed.getBytes();
            for (int i = 0; i < bytes.length; i++) {
                this.number[~i & 3] ^= ((long) bytes[i]) << (((i >> 2) & 7) << 3);
            }

            for (int i = 0; i < 11; i++) {
                long a = this.number[i & 3];
                long b = this.number[(i + 1) & 3];
                long c = this.number[(i + 2) & 3];
                long d = this.number[(i + 3) & 3];

                a += b;
                d ^= a;
                d = (d << 32) | (d >>> 32);
                c += d;
                b ^= c;
                b = (b << 24) | (b >>> 40);
                a += b;
                d ^= a;
                d = (d << 16) | (d >>> 48);
                c += d;
                b ^= c;
                b = (b << 7) | (b >>> 57);

                this.number[0] = a;
                this.number[1] = b;
                this.number[2] = c;
                this.number[3] = d;
            }
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null) {
                return false;
            }
            if (object.getClass() != this.getClass()) {
                return false;
            }

            Integer256 other = (Integer256) object;

            return ((this.number[0] == other.number[0]) &&
                    (this.number[1] == other.number[1]) &&
                    (this.number[2] == other.number[2]) &&
                    (this.number[3] == other.number[3]));
        }

        public String toString() {
            return String.format("%016x%016x%016x%016x", number[3], number[2], number[1], number[0]);
        }
    }
}
