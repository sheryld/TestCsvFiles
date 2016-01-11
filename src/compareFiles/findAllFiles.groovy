
package compareFiles;
import static groovy.io.FileType.FILES
Find f = new Find()
f.findFullPathFile("/Volumes/KFS/Projects_Kuali/sql-reports/kfs/","SELECT_REPORT_FYE_ENCUMBRANCE_BALANCE_BY_FISCAL_YEAR.sql")
class Find {
	def path1
	def path2
	def pattern
	int maxChars
	def log1 = new FileOutputStream("/Users/kuali/Documents/diffResults/diff.logs")
    def log2 = new FileOutputStream("/tmp/outputerr.log")
	
	def fileFinder={a->
		List<String>alist = []
		def dir = new File(a) 
		                  
		dir.eachFileRecurse (FILES){ file ->  
							  
							  alist.add(file.name)
                      
					 
                }
						  return alist.sort()
	}
String findFullPathFile (
	def directoryName,
	def fileSubStr  ){
	def filePattern = ~/${fileSubStr}/
	def directory = new File(directoryName)
	if (!directory.isDirectory())
	{
	   println "The provided directory name ${directoryName} is NOT a directory."
	   System.exit(-2)
	}
	println "Searching for files including ${fileSubStr} in directory ${directoryName}..."
	def findFilenameClosure =
	{
	   if (filePattern.matcher(it.name).find())
	   {
		  println "\t${it.name}${it.getAbsolutePath()} (size ${it.size()})"
		  return it.getAbsolutePath()
		  
	   }
	}
	println "Matching Files:"
	directory.eachFileRecurse(findFilenameClosure)
	}
	void compareFiles (ArrayList<String> a,ArrayList<String> b) {
		def results = null
		
		a.each{String fName->
			def file1 = fName
			def length = file1.size()> maxChars?maxChars:file1.size()
			String matcher = file1.substring(0,length)
			println matcher
			results = b.findLastIndexOf{
				it.contains(file1)
			}
			
			results ?:compare(this.path1 + file1,this.path2 + b.get(results))
		
		}
	}
	

	def compare = {
		
		 a,b->
		def process = "diff  -EbwB  $a  $b ".execute()             
                   process.waitForProcessOutput(log1, log2);
                  }

	def print = {}
}

 