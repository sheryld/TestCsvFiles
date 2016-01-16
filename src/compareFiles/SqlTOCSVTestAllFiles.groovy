package compareFiles

println "start"
def cli = new CliBuilder(usage: 'groovy SqlTOCSVTestAllFIles -p property_file -c config_file')
cli.p(args: 1, required: true, type: String, 'property file to run with sqlTOCsv')
cli.c(args: 1, required: true, type: String, 'config file to run with containing directory')
def opt = cli.parse(args)

if (!opt) {
    System.err.println "Nothing parsed from command line..."
    System.exit(1)
}

if (!opt?.p) {
    cli.usage()
    System.exit(1)
}
if (!opt?.c) {
    cli.usage()
    System.exit(1)
}



def prop = opt.p
def cfg = opt.c



SqlTOCSVAllFiles testAll = new SqlTOCSVAllFiles()
testAll.prop = prop
testAll.load_properties(cfg)
testAll.cleanup()
testAll.testSqlWOParms()
testAll.testSqlWParms()
testAll.doCompare()
println "end"

class SqlTOCSVAllFiles {
    def  difDir = [
            "",
            ""
    ]
    public String prop
    public Find find = new Find()
    //get properties file with a  list of sqltocsv files
    // for each file run sqltoCSV with out parms old put in old directory
    // for each file run sqltoCSV with out parms old put in new directory
    //for each file diff


    def doCompare = {
        owner.find.with {
            path1 = difDir[0]
            path2 = difDir[1]
            pattern = ''
            maxChars = 30
        }
        owner.find.compareFiles(find.fileFinder(find.path1), find.fileFinder(find.path2))
    }
    def load_properties = { fname ->
        Properties props = new Properties()
        new File(fname).withInputStream {
            props.load it
        }
        owner.difDir[0] = props.dir1?:  "/Users/kuali/Documents/diff1/"
        owner.difDir[1] = props.dir2?: "/Users/kuali/Documents/diff2/"
    }



def cleanup = {
    difDir.each { dir ->
        def directory = new File(dir.trim())

        directory.eachFileRecurse()
                { file ->
                     println "Deleting ${file}..."
                    file.delete()

                }

    }
    doCompare
}
def testSqlWOParms = {
    def jarToUseSqlToCSV = [
            "/Volumes/KFS/Projects_Kuali/TestCsvFiles/resources/old/SqlToCsv.jar",
            "/Volumes/KFS/Projects_Kuali/TestCsvFiles/resources/new/SqlToCsvParms.jar"
    ]
    def lineNo = 1

    SqlTOCSVTestAllFiles.getResourceAsStream("/sqlFiles.txt").eachLine { line ->
        String fileName = line
        int cnt = 0

        def temp = fileName?.tokenize('/')
        def temps = temp.size
        if (temps == 0) {
            return;
        }
        String tName = temp[temps - 1]?.replace(".sql", ".csv")
        def log2 = new FileOutputStream("/tmp/outputerr1.log").each { l2 ->
            jarToUseSqlToCSV.each { jarFile ->
                def log1
                def dir = difDir[cnt++]
                log1 = new FileOutputStream("$dir${tName}")
                def cmd = "java -jar $jarFile -p ${owner.prop} ${fileName} "
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
            "/Volumes/KFS/Projects_Kuali/TestCsvFiles/resources/old/sql_to_csv_parms/SqlToCsvParms.jar",
            "/Volumes/KFS/Projects_Kuali/TestCsvFiles/resources/new/sql_to_csv_parms/SqlToCsvParms.jar"
    ]


    def idx = 0
    List filenameswparms = new ArrayList()
    def ret = new XmlFinder().getFilesNames("/sqlWithParmFiles.xml", filenameswparms)
    ret.each { fname ->
        String tName = fname.toString().replace(".sql", ".csv")
        String cmds = filenameswparms.get(idx++)

        def log2 = new FileOutputStream("/tmp/outputerr1.log").each { l2 ->
            def cnt = 0
            jarToUseSqlToCSVParms.each { jarFile ->
                def log1
                def dir = difDir[cnt++]
                log1 = new FileOutputStream("$dir${tName}")
                def cmd = "java -jar $jarFile  -p ${owner.prop} ${cmds}  "
                println cmd
                def process = cmd.execute()
                process.waitForProcessOutput(log1, l2)
                log1.close()
            }
        }
    }
}
}