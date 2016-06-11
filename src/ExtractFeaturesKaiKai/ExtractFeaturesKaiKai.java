package ExtractFeaturesKaiKai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ExtractFeaturesKaiKai.SourceVisitor;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class ExtractFeaturesKaiKai {

	static final String filePath = "test.java";
	static final String rightfiledirectoryPath = "D:\\checkedDataSet\\largeMIX";
	static final String wrongfiledirectoryPath = "D:\\checkedDataSet\\largehandmade";
	static String directoryPath;
	static Map<Integer, Integer> filevec;
	static int ValNum[];

	static List<File> fileList = new ArrayList<File>();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		/**
		 * �Ώۃt�@�C���̎w��
		 */
		// File file = new File(filePath);
		File fileout = new File("output.arff");
		FileWriter filewriter = new FileWriter(fileout);
		BufferedWriter bw = new BufferedWriter(filewriter);
		PrintWriter pw = new PrintWriter(bw);

		File fileout2 = new File("PathOfInstances.txt");
		FileWriter filewriter2 = new FileWriter(fileout2);
		BufferedWriter bw2 = new BufferedWriter(filewriter2);
		PrintWriter pw2 = new PrintWriter(bw2);
		int NumFile = 1;

		pw.println("@relation SourceStateVector\n");
		// 1
		pw.println("@attribute ANONYMOUS_CLASSDECLARATION numeric");
		pw.println("@attribute ARRAY_ACCESS numeric");
		pw.println("@attribute ARRAY_CREATION  numeric");
		pw.println("@attribute ARRAY_INITIALIZER   numeric");
		pw.println("@attribute ARRAY_TYPE numeric");

		pw.println("@attribute ASSERT_STATEMENT numeric");
		pw.println("@attribute ASSIGNMENT numeric");
		pw.println("@attribute BLOCK numeric");
		pw.println("@attribute BOOLEAN_LITERAL numeric");
		pw.println("@attribute BREAK_STATEMENT numeric\n");
		// 11
		pw.println("@attribute CAST_EXPRESSION numeric");
		pw.println("@attribute CATCH_CLAUSE  numeric");
		pw.println("@attribute CHARACTER_LITERAL numeric");
		pw.println("@attribute CLASS_INSTANCE_CREATION numeric");
		pw.println("@attribute COMPILATION_UNIT  numeric");

		pw.println("@attribute CONDITIONAL_EXPRESSION numeric");
		pw.println("@attribute CONSTRUCTOR_INVOCATION  numeric");
		pw.println("@attribute CONTINUE_STATEMENT numeric");
		pw.println("@attribute DO_STATEMENT numeric");
		pw.println("@attribute EMPTY_STATEMENT  numeric\n");

		// 21
		pw.println("@attribute EXPRESSION_STATEMENT numeric");
		pw.println("@attribute FIELD_ACCESS numeric");
		pw.println("@attribute FIELD_DECLARATION numeric");
		pw.println("@attribute FOR_STATEMENT  numeric");
		pw.println("@attribute IF_STATEMENT numeric");

		pw.println("@attribute IMPORT_DECLARATION numeric");
		pw.println("@attribute INFIX_EXPRESSION numeric");
		pw.println("@attribute INITIALIZER numeric");
		// pw.println("@attribute JAVADOC numeric");
		pw.println("@attribute LABELED_STATEMENT numeric\n");

		// 31
		pw.println("@attribute METHOD_DECLARATION numeric");
		pw.println("@attribute METHOD_INVOCATION numeric");
		pw.println("@attribute NULL_LITERAL numeric");
		pw.println("@attribute NUMBER_LITERAL numeric");
		pw.println("@attribute PACKAGE_DECLARATION numeric");

		pw.println("@attribute PARENTHESIZED_EXPRESSION numeric");
		pw.println("@attribute POSTFIX_EXPRESSION numeric");
		pw.println("@attribute PREFIX_EXPRESSION numeric");
		pw.println("@attribute PRIMITIVE_TYPE numeric");
		pw.println("@attribute QUALIFIED_NAME numeric\n");

		// 41
		pw.println("@attribute RETURN_STATEMENT numeric");
		pw.println("@attribute SIMPLE_NAME numeric");
		pw.println("@attribute SIMPLE_TYPE numeric");
		pw.println("@attribute SINGLE_VARIABLE_DECLARATION numeric");
		pw.println("@attribute STRING_LITERAL numeric");

		pw.println("@attribute SUPER_CONSTRUCTOR_INVOCATION numeric");
		pw.println("@attribute SUPER_FIELD_ACCESS numeric\n");
		pw.println("@attribute SUPER_METHOD_INVOCATION numeric");
		pw.println("@attribute SWITCH_CASE numeric");
		pw.println("@attribute SWITCH_STATEMENT numeric\n");

		// 51
		pw.println("@attribute SYNCHRONIZED_STATEMENT numeric");
		pw.println("@attribute THIS_EXPRESSION numeric");
		pw.println("@attribute THROW_STATEMENT numeric");
		pw.println("@attribute TRY_STATEMENT numeric");
		pw.println("@attribute TYPE_DECLARATION  numeric");

		pw.println("@attribute TYPE_DECLARATION_STATEMENT numeric");
		pw.println("@attribute TYPE_LITERAL numeric");
		pw.println("@attribute VARIABLE_DECLARATION_EXPRESSION numeric");
		pw.println("@attribute VARIABLE_DECLARATION_FRAGMENT numeric");
		pw.println("@attribute VARIABLE_DECLARATION_STATEMENT numeric\n");

		// 60
		pw.println("@attribute WHILE_STATEMENT numeric");
		pw.println("@attribute INSTANCEOF_EXPRESSION numeric");
		// pw.println("@attribute LINE_COMMENT numeric");
		// pw.println("@attribute BLOCK_COMMENT numeric");
		pw.println("@attribute TAG_ELEMENT numeric");

		pw.println("@attribute TEXT_ELEMENT numeric");
		pw.println("@attribute MEMBER_REF numeric");
		pw.println("@attribute METHOD_REF numeric");
		pw.println("@attribute METHOD_REF_PARAMETER numeric");
		pw.println("@attribute ENHANCED_FOR_STATEMENT numeric\n");

		// 70
		pw.println("@attribute ENUM_DECLARATION numeric");
		pw.println("@attribute ENUM_CONSTANT_DECLARATION numeric");
		pw.println("@attribute TYPE_PARAMETER numeric");
		pw.println("@attribute PARAMETERIZED_TYPE numeric");
		pw.println("@attribute QUALIFIED_TYPE numeric");

		pw.println("@attribute WILDCARD_TYPE numeric");
		pw.println("@attribute NORMAL_ANNOTATION numeric");
		pw.println("@attribute MARKER_ANNOTATION numeric");
		pw.println("@attribute SINGLE_MEMBER_ANNOTATION numeric");
		pw.println("@attribute MEMBER_VALUE_PAIR numeric\n");

		// 80
		pw.println("@attribute ANNOTATION_TYPE_DECLARATION numeric");
		pw.println("@attribute ANNOTATION_TYPE_MEMBER_DECLARATION numeric");
		pw.println("@attribute MODIFIER numeric");
		pw.println("@attribute UNION_TYPE numeric\n");
		pw.println("@attribute GENERATED {TRUE,FALSE}\n");

		pw.println("@data");
		File directory;
		// 29, 63, 64は無視
		for (int j = 0; j < 2; j++) {
			if (j == 0)
				directoryPath = rightfiledirectoryPath;// まず正解
			else
				directoryPath = wrongfiledirectoryPath;

			directory = null;
			fileList.clear();

			directory = new File(directoryPath);
			if (directory.exists()) {
				makeFileList(directory);// directoryを渡せば，再帰的にたどって
										// FileListに.javaのファイルを記録してくれる．
			} else {
				System.out.println("target directory does not exist.");
				System.exit(0);
			}

			for (File file : fileList)
				System.out.println(file.getName().toString());
			// System.out.println(fileList.size());
			for (File file : fileList) {
				/**
				 * ソースコードのAST構築
				 */
				// System.out.println(file.getName()+"を処理するやで");
				BufferedReader reader = new BufferedReader(new FileReader(file));
				StringBuffer sb = new StringBuffer();
				String st = null;
				while ((st = reader.readLine()) != null) {
					sb.append(st + System.getProperty("line.separator"));
				}
				reader.close();
				ASTParser parser = ASTParser.newParser(AST.JLS4);
				parser.setSource(sb.toString().toCharArray());
				CompilationUnit unit = (CompilationUnit) parser
						.createAST(new org.eclipse.core.runtime.NullProgressMonitor());

				/**
				 * ソースコードのASTNODEの要素数カウント開始
				 */
				SourceVisitor visitor = new SourceVisitor(unit);
				unit.accept(visitor);
				ValNum = new int[85];// 0-84 0は使わない
				/*
				 * List<MethodData> methodList = visitor.getMethodList();
				 *
				 *
				 *
				 * for (MethodData method : methodList) { VectorData vector =
				 * method.getVector();
				 *
				 * Map<Integer, Integer> vectorMap = vector.getVector(); for
				 * (int key = 1; key <= 84; key++) { if
				 * (vectorMap.containsKey(key)) { //キーを持つなら，加える ValNum[key] +=
				 * vectorMap.get(key); } } }
				 */
				filevec = visitor.vector.getVector();

				for (int key = 1; key <= 84; key++) {
					if (filevec.containsKey(key)) { // キーを持つなら，加える
						ValNum[key] += filevec.get(key); // 代入が正しいだろう
					}
				}
				// if (isBig(ValNum)) {
				for (int i = 1; i <= 85; i++) {// これで１ファイル分
					if (i < 85) {
						if (i == 29 || i == 63 || i == 64)
							continue;
						pw.print(ValNum[i] + ",");
					} else {
						if (j == 0)
							pw.print("TRUE");// 自動生成
						else
							pw.print("FALSE");// 非自動生成
					}
				}
				pw.println("");

				pw2.println(NumFile + ":\t" + file.getCanonicalPath());

				NumFile++;
				// }
			}

			// System.out.println("list size is " + methodList.size());

			System.out.println(j + "終わったで．");
		}
		pw.close();
		pw2.close();
	}

	private static void makeFileList(File file) {
		if (file.isDirectory()) {
			File[] innerFiles = file.listFiles();
			for (File tmp : innerFiles) {
				makeFileList(tmp);
			}
		} else if (file.isFile()) {
			if (file.getName().endsWith(".java")) {
				fileList.add(file);
			}
		}
	}

	public static boolean isBig(int[] num) {
		boolean big = false;
		for (int i = 1; i <= 81; i++) {
			if (num[i] >= 10)
				big = true;
		}
		return big;
	}

}
