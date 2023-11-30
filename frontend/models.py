from dataclasses import dataclass

@dataclass
class Show:
    name: str
    shifts: list

@dataclass
class Shift:
    shiftid: str
    showid: str
    start: str
    end: str
    role: str