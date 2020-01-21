# operator-string-decomposition

  Uses a composed string input (usually in the form 'VALUE UNIT') and splits it using a configurable pattern.
  The value in front of the first match is retrieved and given in a configurable named output field.

## Inputs

* composed (string): Composed reading from device (usually in the form 'VALUE UNIT')


## Outputs

* value (float): Decomposed value. This field can be renamed using the 'outputName' config.

## Configs
* outputName: Changes the name of the output field. Defaults to 'value'
* pattern: Changes the default pattern used for splitting. Defaults to '\s'
