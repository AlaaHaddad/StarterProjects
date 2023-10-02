# setup.py: The setup file
from setuptools import setup, Extension

setup(name='kmeanssp',
      version='1.1',
      author="Siba, Alaa",
      description='kmeans algorithem for Software Project class',
      ext_modules=[Extension('kmeanssp',['spkmeans.c',  'spkmeansmodule.c'])])
