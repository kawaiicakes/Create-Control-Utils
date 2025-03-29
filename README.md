## Notes
- Architectury does not merge jars; When you build, you get separate jars for each loader.
  There is an independent project that can merge these into one if desired called
  [Forgix](https://github.com/PacifistMC/Forgix).
- The file names and versions of jars are configured in the root [build.gradle](build.gradle). Feel 
free to change the format if desired, but make sure it follows SemVer to work well on Fabric.
- When publishing, you should always let GitHub Actions build your release jars. These jars are built in predictable
environments and have build metadata.

## License

This template is available under the CC0 license. Feel free to do as you wish with it.
