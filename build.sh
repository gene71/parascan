make clean
make parascan
make jar
rm -r build
mkdir build
cp -r res build
mv ParaJar.jar build
cp runPara.sh build
