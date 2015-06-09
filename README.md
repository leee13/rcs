# rcs
Rcs is the util for creating random sequence.


example:

		String stringPattern = "IT-${S(3)}";
		
		RCSUtil rcsUtil = RCSUtil.getInstance();
		
		String id = rcsUtil.createRandomCharSequence(stringPattern);
		
		System.out.println(stringPattern);
		System.out.println(id);

		
output:

		IT-001