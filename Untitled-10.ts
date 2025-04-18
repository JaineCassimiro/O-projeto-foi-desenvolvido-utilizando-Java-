import { Fragment } from "react";
import CurrencyConverter from "@/components/currency-converter";
import ExchangeRateChart from "@/components/exchange-rate-chart";
import ConversionHistory from "@/components/conversion-history";

export default function Home() {
  return (
    <Fragment>
      <main className="container mx-auto px-4 py-8 flex-grow">
        <CurrencyConverter />
        <ExchangeRateChart />
        <ConversionHistory />
      </main>
    </Fragment>
  );
}
