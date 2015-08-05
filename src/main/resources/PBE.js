scope({c0_Algorithm:7, c0_Cipher:3, c0_Description:2, c0_Digest:3, c0_SymmetricBlockCipher:3, c0_SymmetricCipher:3, c0_Task:2, c0_blockSize:3, c0_insecure:7, c0_keySize:3, c0_memory:3, c0_name:7, c0_outputSize:3, c0_performance:7, c0_secure:7, c0_status:7});
defaultScope(1);
intRange(-8, 7);
stringLength(34);

c0_Algorithm = Abstract("c0_Algorithm");
c0_Task = Abstract("c0_Task");
c0_Digest = Abstract("c0_Digest").extending(c0_Algorithm);
c0_KeyDerivationAlgorithm = Abstract("c0_KeyDerivationAlgorithm").extending(c0_Algorithm);
c0_Cipher = Abstract("c0_Cipher").extending(c0_Algorithm);
c0_SymmetricCipher = Abstract("c0_SymmetricCipher").extending(c0_Cipher);
c0_SymmetricBlockCipher = Abstract("c0_SymmetricBlockCipher").extending(c0_SymmetricCipher);
c0_name = c0_Algorithm.addChild("c0_name").withCard(1, 1);
c0_performance = c0_Algorithm.addChild("c0_performance").withCard(1, 1);
c0_status = c0_Algorithm.addChild("c0_status").withCard(1, 1).withGroupCard(1, 1);
c0_secure = c0_status.addChild("c0_secure").withCard(0, 1);
c0_insecure = c0_status.addChild("c0_insecure").withCard(0, 1);
c0_outputSize = c0_Digest.addChild("c0_outputSize").withCard(1, 1);
c0_Description = c0_Task.addChild("c0_Description").withCard(1, 1);
c0_memory = c0_Cipher.addChild("c0_memory").withCard(1, 1);
c0_keySize = c0_SymmetricCipher.addChild("c0_keySize").withCard(1, 1);
c0_blockSize = c0_SymmetricBlockCipher.addChild("c0_blockSize").withCard(1, 1);
c0_Ciphers = Clafer("c0_Ciphers").withCard(1, 1);
c0_AES128 = c0_Ciphers.addChild("c0_AES128").withCard(1, 1).extending(c0_SymmetricBlockCipher);
c0_AES256 = c0_Ciphers.addChild("c0_AES256").withCard(1, 1).extending(c0_SymmetricBlockCipher);
c0_DES = c0_Ciphers.addChild("c0_DES").withCard(1, 1).extending(c0_SymmetricBlockCipher);
c0_DigestAlgorithms = Clafer("c0_DigestAlgorithms").withCard(1, 1);
c0_md5 = c0_DigestAlgorithms.addChild("c0_md5").withCard(1, 1).extending(c0_Digest);
c0_sha_1 = c0_DigestAlgorithms.addChild("c0_sha_1").withCard(1, 1).extending(c0_Digest);
c0_sha_256 = c0_DigestAlgorithms.addChild("c0_sha_256").withCard(1, 1).extending(c0_Digest);
c0_KeyDerivationAlgorithms = Clafer("c0_KeyDerivationAlgorithms").withCard(1, 1);
c0_pbkdf = c0_KeyDerivationAlgorithms.addChild("c0_pbkdf").withCard(1, 1).extending(c0_KeyDerivationAlgorithm);
c0_PasswordBasedEncryption = Clafer("c0_PasswordBasedEncryption").withCard(1, 1).extending(c0_Task);
c0_kda = c0_PasswordBasedEncryption.addChild("c0_kda").withCard(1, 1);
c0_digest = c0_PasswordBasedEncryption.addChild("c0_digest").withCard(1, 1);
c0_cipher = c0_PasswordBasedEncryption.addChild("c0_cipher").withCard(1, 1);
c0_PASS = Clafer("c0_PASS").withCard(1, 1).extending(c0_Task);
c0_name.refTo(string);
c0_performance.refTo(Int);
c0_outputSize.refTo(Int);
c0_Description.refTo(string);
c0_memory.refTo(Int);
c0_keySize.refTo(Int);
c0_blockSize.refTo(Int);
c0_kda.refTo(c0_KeyDerivationAlgorithm);
c0_digest.refTo(c0_Digest);
c0_cipher.refTo(c0_SymmetricBlockCipher);
c0_AES128.addConstraint(equal(joinRef(join($this(), c0_name)), constant("\"AES with 128bit key\"")));
c0_AES128.addConstraint(equal(joinRef(join($this(), c0_performance)), constant(3)));
c0_AES128.addConstraint(some(join(join($this(), c0_status), c0_secure)));
c0_AES128.addConstraint(equal(joinRef(join($this(), c0_memory)), constant(1)));
c0_AES128.addConstraint(equal(joinRef(join($this(), c0_keySize)), constant(128)));
c0_AES128.addConstraint(equal(joinRef(join($this(), c0_blockSize)), constant(128)));
c0_AES256.addConstraint(equal(joinRef(join($this(), c0_name)), constant("\"AES with 256bit key\"")));
c0_AES256.addConstraint(equal(joinRef(join($this(), c0_performance)), constant(3)));
c0_AES256.addConstraint(some(join(join($this(), c0_status), c0_secure)));
c0_AES256.addConstraint(equal(joinRef(join($this(), c0_memory)), constant(1)));
c0_AES256.addConstraint(equal(joinRef(join($this(), c0_keySize)), constant(256)));
c0_AES256.addConstraint(equal(joinRef(join($this(), c0_blockSize)), constant(128)));
c0_DES.addConstraint(equal(joinRef(join($this(), c0_name)), constant("\"DES\"")));
c0_DES.addConstraint(equal(joinRef(join($this(), c0_performance)), constant(2)));
c0_DES.addConstraint(equal(joinRef(join($this(), c0_memory)), constant(2)));
c0_DES.addConstraint(some(join(join($this(), c0_status), c0_secure)));
c0_DES.addConstraint(equal(joinRef(join($this(), c0_keySize)), constant(56)));
c0_DES.addConstraint(equal(joinRef(join($this(), c0_blockSize)), constant(64)));
c0_md5.addConstraint(equal(joinRef(join($this(), c0_name)), constant("\"MD5\"")));
c0_md5.addConstraint(equal(joinRef(join($this(), c0_performance)), constant(4)));
c0_md5.addConstraint(some(join(join($this(), c0_status), c0_insecure)));
c0_md5.addConstraint(equal(joinRef(join($this(), c0_outputSize)), constant(128)));
c0_sha_1.addConstraint(equal(joinRef(join($this(), c0_name)), constant("\"SHA-1\"")));
c0_sha_1.addConstraint(equal(joinRef(join($this(), c0_performance)), constant(4)));
c0_sha_1.addConstraint(some(join(join($this(), c0_status), c0_insecure)));
c0_sha_1.addConstraint(equal(joinRef(join($this(), c0_outputSize)), constant(160)));
c0_sha_256.addConstraint(equal(joinRef(join($this(), c0_name)), constant("\"SHA-256\"")));
c0_sha_256.addConstraint(equal(joinRef(join($this(), c0_outputSize)), constant(256)));
c0_sha_256.addConstraint(some(join(join($this(), c0_status), c0_secure)));
c0_sha_256.addConstraint(equal(joinRef(join($this(), c0_performance)), constant(2)));
c0_pbkdf.addConstraint(equal(joinRef(join($this(), c0_name)), constant("\"PBKDF\"")));
c0_pbkdf.addConstraint(equal(joinRef(join($this(), c0_performance)), constant(2)));
c0_pbkdf.addConstraint(some(join(join($this(), c0_status), c0_secure)));
c0_PasswordBasedEncryption.addConstraint(equal(joinRef(join($this(), c0_Description)), constant("\"Encrypt data based on a password\"")));
c0_PASS.addConstraint(equal(joinRef(join($this(), c0_Description)), constant("\"Test\"")));
