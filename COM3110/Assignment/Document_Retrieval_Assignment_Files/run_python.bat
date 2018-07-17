python ir_engine.py -w binary -o result.txt
python eval_ir.py -f cacm_gold_std.txt result.txt
python ir_engine.py -s -w binary -o result.txt
python eval_ir.py -f cacm_gold_std.txt result.txt
python ir_engine.py -p -w binary -o result.txt
python eval_ir.py -f cacm_gold_std.txt result.txt
python ir_engine.py -s -p -w binary -o result.txt
python eval_ir.py -I cacm_gold_std.txt result.txt