package Day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SysDir {
    private final SysDir parent;
    String name;
    List<SysDir> sysDirs;
    List<SysFile> sysFiles;

    @Override
    public String toString() {
        return "SysDir{" +
                "name='" + name + '\'' +
                ", files size=" + getFilesSize() +
                '}';
    }
    public String getPath(){
        if(this.getName().equals("/")){
            return "/";
        }
        return parent.getPath()+this.getName()+"/";
    }
    public SysDir(String name, Optional<SysDir> parent) {
        this.name = name;
        this.sysDirs = new ArrayList<>();
        this.sysFiles = new ArrayList<>();
        this.parent = parent.orElse(this);
    }

    public String getName() {
        return name;
    }

    public List<SysDir> getSysDirs() {
        return sysDirs;
    }

    public SysDir getParent() {
        return parent;
    }

    public void addFile(String name, Integer size) {
        SysFile sysFile = new SysFile(name, size);
        sysFiles.add(sysFile);
        System.out.println("file is added "+name);
    }

    public Integer getSizeOfDir() {
        return getFilesSize() + getSizeSubDirs();
    }

    public Integer getFilesSize() {
        Integer total = sysFiles.stream().map(f -> f.size).mapToInt(i -> i).sum();
        //System.out.println("getFilesSize of dir " + name + " : "+total);
        return total;
    }

    public Integer getSizeSubDirs() {
        Integer totalSizeWithSub = 0;
        List<SysDir> sysDirList = this.getSysDirs();
        for (int i = 0; i < sysDirList.size(); i++) {
            SysDir currentDir = sysDirList.get(i);
            totalSizeWithSub = totalSizeWithSub + currentDir.getFilesSize();
            if (currentDir.getSysDirs().size() > 0) {
                totalSizeWithSub = totalSizeWithSub + currentDir.getSizeSubDirs();
            }

        }
        //System.out.println("getSizeSubDirs " + this.getName() + " : " + totalSizeWithSub);
        return totalSizeWithSub;
    }

    public class SysFile {
        String name;
        Integer size;

        public SysFile(String name, Integer size) {
            this.name = name;
            this.size = size;
        }
    }
}
