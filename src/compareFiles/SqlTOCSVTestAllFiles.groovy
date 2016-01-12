package compareFiles

println "start"

SqlTOCSVAllFiles testAll = new SqlTOCSVAllFiles()
testAll.cleanup()
testAll.testSqlWOParms()
testAll.testSqlWParms()
testAll.doCompare()
println "end"

class SqlTOCSVAllFiles {
   public Find find = new Find()
    //get properties file with a  list of sqltocsv files
    // for each file run sqltoCSV with out parms old put in old directory
    // for each file run sqltoCSV with out parms old put in new directory
    //for each file diff
    def difDir = [
            "/Users/kuali/Documents/diff1/",
            "/Users/kuali/Documents/diff2/"
    ]

    def doCompare = {
        
        owner.find.with {
            path1 = difDir[0]
            path2 = difDir[1]
            pattern = ''
            maxChars = 30
        }
        owner.find.compareFiles(find.fileFinder(find.path1), find.fileFinder(find.path2))
    }
    def cleanup = {
        difDir.each { dir ->
            def directory = new File(dir)
            def classPattern = ~/.*\.csv/
            directory.eachFileRecurse(groovy.io.FileType.FILES)
                    { file ->
                        //if (file ==~ classPattern)
                        //{
                        println "Deleting ${file}..."
                        file.delete()
                        //}
                    }

        }
    }
    def testSqlWOParms = {
        def jarToUseSqlToCSV = [
                "/Volumes/KFS/Projects_Kuali/kfs-trunk/work/utilities/sql_to_csv/SqlToCsv.jar",
                "/Volumes/KFS/Projects_Kuali/kfs-trunk/work/utilities/sql_to_csv_parms/SqlToCsvParms.jar"
        ]
        def lineNo = 1
        new File("/Users/kuali/Documents/sqlFiles.txt").eachLine { line ->
            String fileName = line
            int cnt = 0

            def temp = fileName?.tokenize('/')
            def temps = temp.size
            if (temps == 0) {
                return;
            }
            String tName = temp[temps - 1].replace(".sql", ".csv")
            def log2 = new FileOutputStream("/tmp/outputerr1.log").each { l2 ->
                jarToUseSqlToCSV.each { jarFile ->


                    def log1
                    def dir = difDir[cnt++]
                    log1 = new FileOutputStream("$dir${tName}")
                    def cmd = "java -jar $jarFile -p /Users/kuali/sqlToCsv.properties ${fileName} "
                    println cmd
                    def process = cmd.execute()
                    process.waitForProcessOutput(log1, l2);
                    log1.close()
                }
            }
        }




    }

//get properties file with a  list of sqltocsv files
// for each file run sqltoCSV with out parms old put in old directory
// for each file run sqltoCSVParms with out parms old put in new directory
//for each file diff

    def testSqlWParms = {
        def jarToUseSqlToCSVParms = [
                "/Volumes/KFS/Projects_Kuali/kfs-staging/work/utilities/sql_to_csv_parms/SqlToCsvParms.jar",
                "/Volumes/KFS/Projects_Kuali/kfs-trunk/work/utilities/sql_to_csv_parms/SqlToCsvParms.jar"
        ]


        def idx = 0
        List filenameswparms = new ArrayList()
        def ret = new XmlFinder().getFilesNames("/Users/kuali/Documents/sqlWithParmFiles.xml", filenameswparms)
        ret.each { fname ->
            String tName = fname.toString().replace(".sql", ".csv")
            String cmds = filenameswparms.get(idx++)

            def log2 = new FileOutputStream("/tmp/outputerr1.log").each { l2 ->
                def cnt = 0
                jarToUseSqlToCSVParms.each { jarFile ->


                    def log1
                    def dir = difDir[cnt++]
                    log1 = new FileOutputStream("$dir${tName}")
                    def cmd = "java -jar $jarFile -p /Users/kuali/sqlToCsv.properties ${cmds}  "
                    println cmd
                    def process = cmd.execute()
                    process.waitForProcessOutput(log1, l2);
                    log1.close()
                }
            }


        }
    }
}