import http from "http";

import fs from "fs";

import fse from "fs-extra";

import {v4} from "uuid";

import tempDir from "temp-dir";

import unzipper from "unzipper";

const sourceUrl = "http://localhost:8080/ts.zip";
const tmpPath = tempDir + '/' + v4();
const generatePath = "src/__generated"

console.log("Downloading " + sourceUrl + "...");

const request = http.get(sourceUrl, function(response) {
    const extractor = unzipper.Extract({path: tmpPath});
    response.pipe(extractor);
    extractor.on("close", function() {
        console.log(
            "Downloaded & unzipped " + sourceUrl +
            ", the unzipped path is " + tmpPath
        );
        fs.rmSync(generatePath, { recursive: true, force: true });
        console.log("Delete generate path: " + generatePath);

        fse.moveSync(tmpPath, generatePath, { overwrite: true });
        console.log("Move " + tmpPath + " to " + generatePath);

        console.log("Api code is refreshed sucessfully");
    });
});